package com.sdrg.sell.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductForm {

    private String productId;

//    @NotEmpty(message = "商品名必填")
    private String productName;

//    @NotEmpty(message = "商品价格必填")
    private BigDecimal productPrice;

//    @NotEmpty(message = "商品库存必填")
    private String productStock ;

//    @NotEmpty(message = "商品描述必填")
    private String productDescription;

//    @NotEmpty(message = "商品图片必填")
    private String productIcon ;

//    @NotEmpty(message = "商品状态必填")
    private String productStatus;

//    @NotEmpty(message = "类目类型必填")
    private String categoryType;
}
