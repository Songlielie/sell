package com.sdrg.sell.services.Impl;

import com.sdrg.sell.Utils.KeyUtil;
import com.sdrg.sell.converter.OrderMaster2OrderDTO;
import com.sdrg.sell.dataobject.OrderDetail;
import com.sdrg.sell.dataobject.OrderMaster;
import com.sdrg.sell.dataobject.ProductInfo;
import com.sdrg.sell.dto.CartDTO;
import com.sdrg.sell.dto.OrderDTO;
import com.sdrg.sell.enums.OrderPayStatusEnum;
import com.sdrg.sell.enums.OrderStatusEnum;
import com.sdrg.sell.enums.ResultEnum;
import com.sdrg.sell.exception.SellException;
import com.sdrg.sell.respository.OrderDetailRepository;
import com.sdrg.sell.respository.OrderMasterRepository;
import com.sdrg.sell.services.OrderService;
import com.sdrg.sell.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = KeyUtil.genUniqueKey();
        //1、查询商品价格，库存等
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2、库存比较和计算总价
            if (productInfo.getProductStock() > orderDetail.getProductQuantity()) {
                orderAmount = productInfo.getProductPrice()
                        .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                        .add(orderAmount);
//                productInfo.setProductStock(productInfo.getProductStock()-orderDetail.getProductQuantity());
            } else {
                throw new SellException(ResultEnum.PRODUCT_NOT_ENOUGH);
            }

            //3、数据存入OrderMaster和OrderDetail
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);

            OrderMaster orderMaster = new OrderMaster();
            orderDTO.setOrderId(orderId);
            BeanUtils.copyProperties(orderDTO, orderMaster);
            orderMaster.setOrderAmount(orderAmount);
            orderMasterRepository.save(orderMaster);
        }

        //4、减库存（只想执行一次减库存，方法写在productService中）
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOS);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_MASTER_NOT_EXIST);
        }
        List<OrderDetail> orderDetail = orderDetailRepository.findAllByOrderId(orderId);
        if (orderDetail == null) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetail);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findByBuyerOpenid(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMaster = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        if (orderMaster.isEmpty()) {
            throw new SellException(ResultEnum.OPENID_NOT_EXIST);
        }

        //TODO switch OrderList and PageOrder
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMaster.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMaster.getTotalElements());

        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        //1、判断订单状态，更改订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【取消订单】，订单状态不正确，orderId->{},orderStatus->{}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_MASTER_NOT_NEW_ORDER);
        }
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.info("【取消订单】，订单详情为空，orderDTO->{}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        orderMaster.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderMasterRepository.save(orderMaster);

        //2、增加库存
        List<OrderDetail> orderDetail = orderDetailRepository.findAllByOrderId(orderDTO.getOrderId());
        List<CartDTO> cartDTOList = orderDetail.stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //3、如果已经支付，要退款
        if (orderDTO.getPayStatus().equals(OrderPayStatusEnum.Wait.getCode())) {
            log.info("【取消订单】，未支付，orderDTO->{}", orderDTO);
//            throw new SellException(ResultEnum.ORDER_PAY_STATUS_WAIT);
            //TODO 退钱
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (orderMaster.getPayStatus() == 1 && orderMaster.getOrderStatus() == 0) {
            orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
            orderMasterRepository.save(orderMaster);
        } else {
            throw new SellException(ResultEnum.ORDER_FINISH_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        if (!orderDTO.getPayStatus().equals(OrderPayStatusEnum.Wait)) {
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_NOT_WAIT);
        } else if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW)) {
            throw new SellException(ResultEnum.ORDER_MASTER_NOT_NEW_ORDER);
        }
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        orderMaster.setPayStatus(OrderPayStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findAllList(Pageable pageable) {

        Page<OrderMaster> orderMaster = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMaster.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMaster.getTotalElements());
        return orderDTOPage;
    }
}
