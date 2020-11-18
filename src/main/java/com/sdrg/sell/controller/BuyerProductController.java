package com.sdrg.sell.controller;

import com.sdrg.sell.Utils.VOResultUtil;
import com.sdrg.sell.dataobject.ProductCategory;
import com.sdrg.sell.dataobject.ProductInfo;
import com.sdrg.sell.services.Impl.CategoryServiceImpl;
import com.sdrg.sell.services.Impl.ProductServiceImpl;
import com.sdrg.sell.vResult.VOProduct;
import com.sdrg.sell.vResult.VOProductInfo;
import com.sdrg.sell.vResult.VOResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "123")
    public VOResult all() {
        //1.查询所有的上架商品
        List<ProductInfo> productInfos = productService.findUpAll();

        //2.查询类目
//        List<Integer> list = new ArrayList<>();
//        //传统方法
//        for(ProductInfo productInfo:productInfos){
//            list.add(productInfo.getCategoryType());
//        }
        //精简方法(java8，lambda),上面的传统方式是new一个后add
        List<Integer> list = productInfos.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(list);

        //3.数据拼装
//        VOResult voResult = new VOResult();
//        voResult.setCode(0);
//        voResult.setMsg("成功");
        List<VOProduct> voProducts = new ArrayList<>();
        for (ProductCategory productCategory : productCategories) {
            VOProduct voProduct = new VOProduct();
            voProduct.setCategoryName(productCategory.getCategoryName());
            voProduct.setType(productCategory.getCategoryType());

            List<VOProductInfo> voProductInfos = new ArrayList<>();
            for (ProductInfo productInfo : productInfos) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    VOProductInfo voProductInfo = new VOProductInfo();
                    BeanUtils.copyProperties(productInfo, voProductInfo);
                    voProductInfos.add(voProductInfo);
                }
            }
            voProduct.setProductVOInfo(voProductInfos);
            voProducts.add(voProduct);
        }
        return VOResultUtil.success(voProducts);
//        voResult.setDate(voProducts);
//        return voResult;
//        voProductInfo.setProductId(123456);
//        voProductInfo.setProductName("老婆饼");
//        voProductInfo.setProductDescription("老婆做的");
//        voProductInfo.setProductIcon("http://#");
//        voProductInfo.setProductPrice(new BigDecimal(2.3));
    }

}
