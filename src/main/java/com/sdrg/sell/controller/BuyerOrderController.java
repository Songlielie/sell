package com.sdrg.sell.controller;

import com.sdrg.sell.Utils.VOResultUtil;
import com.sdrg.sell.converter.OrderForm2OrderDTO;
import com.sdrg.sell.dataobject.OrderDetail;
import com.sdrg.sell.dto.OrderDTO;
import com.sdrg.sell.enums.ResultEnum;
import com.sdrg.sell.exception.SellException;
import com.sdrg.sell.form.OpenidForm;
import com.sdrg.sell.form.OrderForm;
import com.sdrg.sell.services.BuyerService;
import com.sdrg.sell.services.Impl.OrderServiceImpl;
import com.sdrg.sell.vResult.VOResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public VOResult<List<OrderDTO>> creatOrder(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单] 参数不正确,orderForm->{}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setBuyerName(orderForm.getBuyerName());
//        orderDTO.setBuyerPhone(orderForm.getBuyerPhone());
//        orderDTO.setBuyerAddress(orderForm.getBuyerAddress());
//        orderDTO.setBuyerOpenid(orderForm.getBuyerOpenid());
        OrderDTO orderDTO = OrderForm2OrderDTO.convertOrderDTO(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.info("[创建订单]购物车不能为空,orderDTO->{}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO1 = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderDTO1.getOrderId());
        return VOResultUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public VOResult<List<OrderDTO>> orderList(@Valid OpenidForm openidForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("[查询订单]参数错误,openid->{}", openidForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        Pageable pageable = PageRequest.of(openidForm.getPage(), openidForm.getSize());
        Page<OrderDTO> orderDTOPage = orderService.findByBuyerOpenid(openidForm.getOpenid(), pageable);
        return VOResultUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public VOResult<List<OrderDetail>> orderDetail(@RequestParam("openid") String openid,
                                                   @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        ;
        return VOResultUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public VOResult orderCancel(@RequestParam("openid") String openid,
                                @RequestParam("orderId") String orderId) {

        OrderDTO orderDTO = buyerService.cancelOrderOne(openid, orderId);
        orderService.cancel(orderDTO);
        return VOResultUtil.success(null);
    }
}
