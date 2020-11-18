package com.sdrg.sell.services.Impl;

import com.sdrg.sell.services.SellerService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SellerServiceImplTest {

    private final static String openid = "abc";

    @Autowired
    private SellerService sellerService;

    @Test
    void findSellerInfoByOpenid() {
        Assert.assertNotNull(sellerService.findSellerInfoByOpenid(openid));
    }
}