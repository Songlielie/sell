package com.sdrg.sell.Utils;

import java.util.Random;

public class KeyUtil {

    /**
     * @Description: 生成唯一的主键
     * @Param:
     * @return:
     * @Author: Mr.Song
     * @Date: 2020-8-12
     */
    public static synchronized String genUniqueKey() {
        Random orderDetailId = new Random();
        Integer number = orderDetailId.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}