package com.sdrg.sell.services.Impl;

import com.sdrg.sell.dataobject.ProductInfo;
import com.sdrg.sell.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void findOne() {
        ProductInfo productInfo = productService.findOne("123456");
        log.info("【产品信息】date={}", productInfo);
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    void findUpAll() {
        Assert.assertNotEquals(0, productService.findUpAll().size());
    }

    @Test
    void findAll() {
        PageRequest pageRequest = PageRequest.of(1, 3);
        Page<ProductInfo> page = productService.findAll(pageRequest);
        System.out.println(page.getTotalElements());
    }

    @Test
    void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("456789");
        productInfo.setProductName("麻花");
        productInfo.setProductPrice(new BigDecimal(2.4));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("老婆做的麻花");
        productInfo.setProductIcon("http:#1");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(4);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}