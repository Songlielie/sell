package com.sdrg.sell.services.Impl;

import com.sdrg.sell.dataobject.ProductInfo;
import com.sdrg.sell.dto.CartDTO;
import com.sdrg.sell.enums.ProductStatusEnum;
import com.sdrg.sell.enums.ResultEnum;
import com.sdrg.sell.exception.SellException;
import com.sdrg.sell.respository.ProductInfoRepository;
import com.sdrg.sell.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

//    @Autowired
//    private ProductService productService;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findById(productId).orElse(null);
        productInfo.setProductStatus(0);
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findById(productId).orElse(null);
        productInfo.setProductStatus(1);
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOS) {

        for (CartDTO cartDTO : cartDTOS) {
            ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
//            ProductInfo productInfo = productService.findOne(cartDTO.getProductId());
            productInfo.setProductStock(productInfo.getProductStock() + cartDTO.getProductQuantity());
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {

        //1、获取cartDTOs中的productId和quantity
        //2、使用ProductInfoRepository找到产品库存
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.getOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_NOT_ENOUGH);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
