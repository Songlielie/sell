package com.sdrg.sell.respository;

import com.sdrg.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = productCategoryRepository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }

//    @Test
//    public void postOneTest(){
//        ProductCategory productCategory = new ProductCategory("低热度",6);
//        ProductCategory result = productCategoryRepository.save(productCategory);
//        Assert.assertNotNull(result);
//    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(5, 6, 7);
        List<ProductCategory> productCategoryList = productCategoryRepository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, productCategoryList);

    }
}