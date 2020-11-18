package com.sdrg.sell.dataobject.mapper;

import com.sdrg.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryMapperTest {

    @Autowired
    public ProductCategoryMapper mapper;

    @Test
    void insertByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("categoryName","老婆上班饼");
        map.put("categoryType",10);
        int value = mapper.insertByMap(map);
        Assert.assertEquals(1,value);
    }

    @Test
    void insertByObject(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("工作饼");
        productCategory.setCategoryType(103);
        int value = mapper.insertByObject(productCategory);
        Assert.assertEquals(1,value);
    }

    @Test
    void selectByCategoryType(){
        ProductCategory productCategory = mapper.selectByCategoryType(10);
        Assert.assertNotNull(productCategory);
    }

    @Test
    void updateByCategoryType(){
//        ProductCategory productCategory = mapper.selectByCategoryType(11);
//        productCategory.setCategoryType(13);
        int value = mapper.updateByCategoryType(13,11);
        Assert.assertEquals(1,value);
    }

    @Test
    void deleteByCategoryType(){
        int result = mapper.deleteByCategoryType(11);
        Assert.assertEquals(1,result);
    }

    /**
     * 测试mybatis查询
     */
    @Test
    void selectByCategoryTypeByXML(){
        ProductCategory productCategory = mapper.selectByCategoryTypeByXML(10);
        Assert.assertNotNull(productCategory);
    }
}