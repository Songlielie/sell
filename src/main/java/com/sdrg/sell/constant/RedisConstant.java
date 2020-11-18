package com.sdrg.sell.constant;

/**
 * @Description: redis常量
 * @Param:
 * @return:
 * @Author: Mr.Song
 * @Date: 2020-8-21
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";
    int EXPIRE = 7200; //5分钟
}
