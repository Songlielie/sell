package com.sdrg.sell.vResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: http返回商品详情
 * @Param:
 * @return:
 * @Author: Mr.Song
 * @Date: 2020-8-7
 */

@Data
public class VOProductInfo implements Serializable {

    private static final long serialVersionUID = -1243382158396405406L;
    /**
     * 商品id
     */
    @JsonProperty("id")
    private String productId;

    /**
     * 商品名
     */
    @JsonProperty("name")
    private String productName;

    /**
     * 商品价格
     */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /**
     * 商品描述
     */
    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
