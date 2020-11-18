package com.sdrg.sell.services;

import com.sdrg.sell.dto.OrderDTO;

public interface BuyerService {

    /**
     * 买家查询你一个订单
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 买家取消一个订单
     */
    OrderDTO cancelOrderOne(String openid, String orderId);

}
