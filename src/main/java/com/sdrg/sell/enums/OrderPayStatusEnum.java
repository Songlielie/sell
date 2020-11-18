package com.sdrg.sell.enums;

import lombok.Getter;

@Getter
public enum OrderPayStatusEnum implements CodeEnum {
    Wait(0, "未支付"),
    FINISHED(1, "已支付");

    private Integer code;
    private String msg;

    OrderPayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
