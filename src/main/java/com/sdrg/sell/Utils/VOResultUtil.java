package com.sdrg.sell.Utils;

import com.sdrg.sell.vResult.VOResult;

public class VOResultUtil {

    public static VOResult success(Object object) {

        VOResult voResult = new VOResult();
        voResult.setCode(0);
        voResult.setMsg("成功");
        voResult.setDate(object);
        return voResult;
    }

    public static VOResult success() {
        return null;
    }

    public static VOResult error(Object object) {
        VOResult voResult = new VOResult();
        voResult.setCode(1);
        voResult.setMsg("失败");
        voResult.setDate(object);
        return voResult;
    }
    public static VOResult error(Integer code,String msg) {
        VOResult voResult = new VOResult();
        voResult.setCode(code);
        voResult.setMsg(msg);
        return voResult;
    }
}
