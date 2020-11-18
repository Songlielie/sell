package com.sdrg.sell.services.Impl;

import com.sdrg.sell.Utils.KeyUtil;
import com.sdrg.sell.Utils.RedisLock;
import com.sdrg.sell.exception.SellException;
import com.sdrg.sell.services.QuerySecKillProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class querySecKillProductInfoImpl implements QuerySecKillProductInfo {

    @Autowired
    private RedisLock redisLock;
    private final static int TIMEOUT = 10 *1000; //过期时间设置为10s

    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;
    static {
        /**
         * 模拟多个表，商品信息表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",100000);
        stock.put("123456",100000);
    }

    @Override
    public String querySecKillProductInfo(String productId) {
        return "商品一共"+products.get(productId)
                +"份，还剩"+stock.get(productId)+"份，已下单"
                +orders.size()+"人。";
    }

    @Override
    public void orderProductMockDiffUser(String productId) {

        long value = System.currentTimeMillis()+TIMEOUT;
        //加锁
        if (!redisLock.lock(productId,String.valueOf(value))){
            throw new SellException(101,"没有进行set，获取锁失败");
        }

        //1.查询该商品库存，为0则活动结束。
        int stockNum = stock.get(productId);
        if (stockNum == 0){
            throw new SellException(100,"活动结束");
        }else {
            //2.下单（模拟不同用户openid不同）
            orders.put(KeyUtil.genUniqueKey(),productId);
            //3.减库存
            stockNum = stockNum -1;
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            stock.put(productId,stockNum);
        }

        //解锁
        redisLock.unlock(productId);
    }
}
