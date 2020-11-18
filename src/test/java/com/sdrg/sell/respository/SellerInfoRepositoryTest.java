package com.sdrg.sell.respository;

import com.sdrg.sell.Utils.KeyUtil;
import com.sdrg.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SellerInfoRepositoryTest {


    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUserName("admin1");
        sellerInfo.setPassWord("admin1");
        sellerInfo.setOpenid("abc1");

        SellerInfo result = sellerInfoRepository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    void findByOpenid() {

        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid("abc1");
        Assert.assertEquals("admin1",sellerInfo.getUserName());
    }
}