package com.sdrg.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum {

    NEW(0, "新订单"),
    FINISHED(1, "完成订单"),
    CANCELED(2, "取消订单");

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

//    public static String getOrderStatus(Integer code) {
//        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
//            if (orderStatusEnum.getCode().equals(code)) {
//                return orderStatusEnum.getMsg();
//            }
//        }
//        return null;
//    }
//
//    public static String getOrderPayStatus(Integer code) {
//        for (OrderPayStatusEnum orderPayStatusEnum : OrderPayStatusEnum.values()) {
//            if (orderPayStatusEnum.getCode().equals(code)) {
//                return orderPayStatusEnum.getMsg();
//            }
//        }
//        return null;
//    }
}

