package com.sdrg.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sdrg.sell.Utils.EnumUtil;
import com.sdrg.sell.Utils.serializer.Date2LongSerializer;
import com.sdrg.sell.dataobject.OrderDetail;
import com.sdrg.sell.enums.OrderPayStatusEnum;
import com.sdrg.sell.enums.OrderStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * data transfer object （dto） 数据传输对象
 */

@Data
@DynamicUpdate
//json结果不返回属性为空的字符
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = -3638975764719327568L;

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    /**
     * 买家微信openid
     */
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 默认0为新下单
     */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**
     * 默认0为未支付
     */
    private Integer payStatus = OrderPayStatusEnum.Wait.getCode();

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;
//
//    private String getOrderStatus(Integer code){
//        return OrderStatusEnum.getOrderStatus(code);
//    }
//
//    private String getOrderPayStatus(Integer code){
//        return OrderStatusEnum.getOrderPayStatus(code);
//    }

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public OrderPayStatusEnum getOrderPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, OrderPayStatusEnum.class);
    }
}
