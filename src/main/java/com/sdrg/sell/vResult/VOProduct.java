package com.sdrg.sell.vResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: http中间请求
 * @Param:
 * @return:
 * @Author: Mr.Song
 * @Date: 2020-8-7
 */

@Data
public class VOProduct implements Serializable {

    private static final long serialVersionUID = -4356253496867377400L;
    /**
     * 商品类目名
     */
    @JsonProperty("name")
    private String categoryName;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 商品详细信息
     */
    @JsonProperty("foods")
    private List<VOProductInfo> productVOInfo;
}
