package com.sdrg.sell.respository;

import com.sdrg.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderDetailRepositoryTest {

    private final String ORDERID = "123458";
    @Autowired
    private OrderDetailRepository repository;

    @Test
    void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("222222");
        orderDetail.setOrderId("123458");
        orderDetail.setProductId("124678");
        orderDetail.setProductPrice(new BigDecimal(5.6));
        orderDetail.setProductQuantity(5);
        orderDetail.setProductIcon("http:#2");
        orderDetail.setProductName("老婆饼2号");

        OrderDetail orderDetail1 = repository.save(orderDetail);
        Assert.assertNotNull(orderDetail1);
    }

    @Test
    void findAllByOrderId() {
        List<OrderDetail> orderDetail = repository.findAllByOrderId(ORDERID);
        Assert.assertEquals(0, orderDetail.size());
    }
}