package com.sdrg.sell.dataobject;

import com.sdrg.sell.enums.OrderPayStatusEnum;
import com.sdrg.sell.enums.OrderStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
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

    @Column(insertable = false)
    private Date createTime;

    @Column(insertable = false)
    private Date updateTime;
}
