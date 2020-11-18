package com.sdrg.sell.respository;

import com.sdrg.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /**
     * 查找上架商品
     */
    List<ProductInfo> findByProductStatus(Integer ProductStatus);
}
