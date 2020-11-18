package com.sdrg.sell.vResult;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: http请求返回最外层的对象
 * @Param:
 * @return:
 * @Author: Mr.Song
 * @Date: 2020-8-7
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VOResult<T> implements Serializable {

    private static final long serialVersionUID = 8032077932253925660L;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示内容
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T date;
}
