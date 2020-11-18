package com.sdrg.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SellerInfo {

    @Id
    private String sellerId;

    private String userName;

    private String passWord;

    private String openid;
}
