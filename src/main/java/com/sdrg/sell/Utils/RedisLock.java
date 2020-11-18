package com.sdrg.sell.Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *@Description:
     *@Param: value 当前时间+过期时间
     *@return: 
     *@Author: Mr.Song
     *@Date: 2020-10-18
    */
    public boolean lock(String key,String value){

        //key的value为空或value的值已过期，是可以获得锁的
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁没过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //获取上一个锁的value
            String oldValue = redisTemplate.opsForValue().getAndSet(key,value);
            if (!StringUtils.isEmpty(oldValue) && currentValue.equals(oldValue)){
                return true;
            }
        }
        return false;
    }

    public void unlock(String key){
        String currentValue = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue)){
            redisTemplate.opsForValue().getOperations().delete(key);
        }
    }

    public void unlock1(String key,String value){
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("【redis分布式锁】解锁异常",e);
        }
    }
}
