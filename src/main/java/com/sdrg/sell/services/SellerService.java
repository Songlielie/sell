package com.sdrg.sell.services;

import com.sdrg.sell.dataobject.SellerInfo;

public interface SellerService {

    /**
     *@Description: 通过Opinid查询卖家信息
     *@Param: 
     *@return: 
     *@Author: Mr.Song
     *@Date: 2020-9-7
    */
    SellerInfo findSellerInfoByOpenid(String openid);

}
