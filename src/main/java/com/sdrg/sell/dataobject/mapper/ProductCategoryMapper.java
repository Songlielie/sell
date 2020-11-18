package com.sdrg.sell.dataobject.mapper;

import com.sdrg.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.Map;

public interface ProductCategoryMapper {

    @Insert("insert into product_category(category_name,category_type) values (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType =INTEGER})")
    int insertByMap(Map<String,Object> map);

    @Insert("insert into product_category(category_name,category_type) values (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType =INTEGER})")
    int insertByObject(ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_name",property = "categoryName")
    })
    ProductCategory selectByCategoryType(Integer categoryType);

    @Update("update product_category set category_type = #{categoryType1} where category_type = #{categoryType}")
    int updateByCategoryType(@Param("categoryType") Integer categoryType,
                             @Param("categoryType1") Integer categoryType1);

    @Update("update product_category set category_type = #{categoryType} where category_id = #{categoryId}")
    int updateByObject(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategoryType(Integer categoryType);

    ProductCategory selectByCategoryTypeByXML(Integer categoryType);
}