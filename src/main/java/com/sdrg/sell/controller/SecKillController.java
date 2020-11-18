package com.sdrg.sell.controller;

import com.sdrg.sell.services.QuerySecKillProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SecKillController {

    @Autowired
    private QuerySecKillProductInfo querySecKillProductInfo;

    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId){
        log.info("@skill request, productId:" + productId);
        querySecKillProductInfo.orderProductMockDiffUser(productId);
        return querySecKillProductInfo.querySecKillProductInfo(productId);
    }
}