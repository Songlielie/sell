package com.sdrg.sell.services.Impl;

import com.sdrg.sell.dto.OrderDTO;
import com.sdrg.sell.enums.ResultEnum;
import com.sdrg.sell.exception.SellException;
import com.sdrg.sell.services.BuyerService;
import com.sdrg.sell.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("[取消订单]查不到订单，orderId-{}", orderId);
            throw new SellException(ResultEnum.ORDER_MASTER_NOT_EXIST);
        }
        return checkOrderOwner(openid, orderId);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("[查询订单]参数错误，openid-{},orderId->{}", openid, orderId);
            throw new SellException(ResultEnum.OPENID_NOT_MATCH);
        }
        return orderDTO;
    }
}
