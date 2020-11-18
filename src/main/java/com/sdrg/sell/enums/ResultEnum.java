package com.sdrg.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "表单参数错误"),
    OPENID_NOT_MATCH(2, "不是本人的openid"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_NOT_ENOUGH(11, "库存不足"),
    PRODUCT_SAVE_SUCCESS(12, "商品信息保存成功"),
    ORDER_MASTER_NOT_EXIST(15, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(16, "订单详情不存在"),
    OPENID_NOT_EXIST(17, "openId不存在"),
    ORDER_MASTER_NOT_NEW_ORDER(18, "订单状态不是新下单"),
    ORDER_PAY_STATUS_NOT_WAIT(19, "订单状态不是未支付"),
    ORDER_PAY_STATUS_WAIT(20, "订单状态未支付"),
    ORDER_FINISH_ERROR(21, "订单完成失败"),
    ORDER_CANCEL_SUCCESS(22, "成功"),
    ORDER_FINISH_SUCCESS(23, "成功"),
    LOGIN_FAILED(24, "登陆信息不存在，登陆失败");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
