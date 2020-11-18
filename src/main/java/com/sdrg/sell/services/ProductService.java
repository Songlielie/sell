package com.sdrg.sell.services;

import com.sdrg.sell.dataobject.ProductInfo;
import com.sdrg.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架产品
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询结果分页
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    ProductInfo onSale(String productId);

    ProductInfo offSale(String productId);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
