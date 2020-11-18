package com.sdrg.sell.services.Impl;

import com.sdrg.sell.dataobject.SellerInfo;
import com.sdrg.sell.respository.SellerInfoRepository;
import com.sdrg.sell.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    public SellerInfo findSellerInfoByOpenid(String openid) {

        return sellerInfoRepository.findByOpenid(openid);
    }
}
