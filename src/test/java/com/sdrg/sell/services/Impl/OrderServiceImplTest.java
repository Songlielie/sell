package com.sdrg.sell.services.Impl;

import com.sdrg.sell.dataobject.OrderDetail;
import com.sdrg.sell.dataobject.OrderMaster;
import com.sdrg.sell.dto.OrderDTO;
import com.sdrg.sell.respository.OrderMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    private final String BUYER_OPENID = "sg19941217";
    private final String ORDER_ID = "1597365931879319316";

    @Test
    void createTest() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("sg");
        orderDTO.setBuyerAddress("没有使用注解");
        orderDTO.setBuyerPhone("110112");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123458");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        System.out.println(result);

        Assert.assertNotNull(orderDTO);
    }

    @Test
    void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);

        log.info("【查询单个订单】 result={}", orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    void findByBuyerOpenid() {
        Page<OrderDTO> orderDTOPage = orderService.findByBuyerOpenid(BUYER_OPENID, PageRequest.of(0, 1));
        log.info("查找的orderDTO={}", orderDTOPage);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderService.cancel(orderDTO);
        OrderMaster orderMaster = orderMasterRepository.findById(ORDER_ID).orElse(null);
        log.info("【订单状态】orderMaster->{}", orderMaster);
        Assert.assertEquals(2, orderMaster.getOrderStatus().intValue());
    }

    @Test
    void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderService.finish(orderDTO);
        OrderMaster orderMaster = orderMasterRepository.findById(ORDER_ID).orElse(null);
        log.info("【订单】orderMaster->{}", orderMaster);
        Assert.assertEquals(1, orderMaster.getOrderStatus().intValue());
    }

    @Test
    void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderService.paid(orderDTO);
        OrderMaster orderMaster = orderMasterRepository.findById(ORDER_ID).orElse(null);
        log.info("【订单】orderMaster->{}", orderMaster);
        Assert.assertEquals(1, orderMaster.getPayStatus().intValue());
    }

    @Test
    void findAllList() {
        Page<OrderDTO> orderDTO = orderService.findAllList(PageRequest.of(0, 2));
        log.info("【查询所有订单】orderMaster->{}", orderDTO.getContent());
        Assert.assertNotEquals(0, orderDTO.getTotalElements());
    }
}