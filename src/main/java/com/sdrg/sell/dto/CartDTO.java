package com.sdrg.sell.dto;

import lombok.Data;

/**
 * @Description: 购物车
 * @Param:
 * @return:
 * @Author: Mr.Song
 * @Date: 2020-8-12
 */

@Data
public class CartDTO {

    /**
     * 商品ID
     */
    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public CartDTO() {
    }
}
