package com.sdrg.sell.Impl;

import com.sdrg.sell.dataobject.ProductCategory;
import com.sdrg.sell.services.Impl.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
//        Assert.assertNotNull(productCategory);
    }

    @Test
    void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<Integer> list = Arrays.asList(5, 6, 7, 8);
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(list);
        Assert.assertNotNull(categories.size());
    }

    @Test
    void save() {
        ProductCategory productCategory = new ProductCategory("老婆专享", 9);
        Assert.assertNotNull(categoryService.save(productCategory));
    }


}