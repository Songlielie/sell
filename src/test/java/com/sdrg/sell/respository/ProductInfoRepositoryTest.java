package com.sdrg.sell.respository;

import com.sdrg.sell.dataobject.ProductInfo;
import com.sdrg.sell.enums.ProductStatusEnum;
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
class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    void saveProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("老婆饼");
        productInfo.setProductPrice(new BigDecimal(5.4));
        productInfo.setProductStock(110);
        productInfo.setProductDescription("老婆做的饼");
        productInfo.setProductIcon("http:#");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(3);

        ProductInfo productInfo1 = productInfoRepository.save(productInfo);
        Assert.assertNotEquals(0, productInfo1);
    }

    @Test
    void findByCategoryStatus() {
        List<ProductInfo> productInfo = productInfoRepository.findByProductStatus(0);
        Assert.assertNotNull(productInfo);
    }
}