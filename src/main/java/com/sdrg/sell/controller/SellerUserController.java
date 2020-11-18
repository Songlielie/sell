package com.sdrg.sell.controller;

import com.sdrg.sell.Utils.CookieUtil;
import com.sdrg.sell.constant.CookieConstant;
import com.sdrg.sell.constant.RedisConstant;
import com.sdrg.sell.dataobject.SellerInfo;
import com.sdrg.sell.enums.ResultEnum;
import com.sdrg.sell.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {

        //1、openid和数据库中匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAILED.getMsg());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //2、设置token至redis
        String token = UUID.randomUUID().toString();
        int expire = RedisConstant.EXPIRE;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        //3、设置token至cookie
        CookieUtil.set(CookieConstant.TOKEN, token, expire,response);

        return new ModelAndView("redirect:http://127.0.0.1:8083/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response,
                       HttpServletRequest request,
                       Map<String, Object> map){
        //1、使用token名查询是否存在cookie
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie != null){
            //2、清除redis
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            //3、清除cookie
            CookieUtil.set(CookieConstant.TOKEN,null,0,response);
        }
    }
}