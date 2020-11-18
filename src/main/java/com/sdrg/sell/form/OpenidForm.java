package com.sdrg.sell.form;

import com.sdrg.sell.enums.OrderStatusEnum;
import com.sdrg.sell.enums.ResultEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OpenidForm {

    @NotEmpty(message = "openid必填")
    private String openid;

    //赋值是为了防止没有传page和size
    private Integer page = OrderStatusEnum.NEW.getCode();

    private Integer size = ResultEnum.PRODUCT_NOT_EXIST.getCode();
}
