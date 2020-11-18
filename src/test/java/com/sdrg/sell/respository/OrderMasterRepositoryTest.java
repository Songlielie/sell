package com.sdrg.sell.respository;

import com.sdrg.sell.dataobject.OrderMaster;
import javassist.runtime.Desc;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String openid = "45678";

    @Test
    void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("sg");
        orderMaster.setBuyerPhone("13172200177");
        orderMaster.setBuyerAddress("江苏大学");
        orderMaster.setBuyerOpenid("45678");
        orderMaster.setOrderAmount(new BigDecimal(2.4));

        OrderMaster orderMaster1 = repository.save(orderMaster);
        Assert.assertEquals(123, orderMaster1.getOrderId());
    }

    @Test
    void findByBuyerOpenid() {
        PageRequest page = PageRequest.of(0, 2);
        Page<OrderMaster> orderMaster = repository.findByBuyerOpenid(openid, page);
        System.out.println(orderMaster.getTotalElements());
        Assert.assertNotNull(orderMaster);
    }
}