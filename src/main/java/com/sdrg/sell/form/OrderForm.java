package com.sdrg.sell.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sdrg.sell.dto.CartDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String buyerName;

    @NotEmpty(message = "手机号必填")
    private String buyerPhone;

    @NotEmpty(message = "地址必填")
    private String buyerAddress;

    @NotEmpty(message = "openid必填")
    private String buyerOpenid;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}
