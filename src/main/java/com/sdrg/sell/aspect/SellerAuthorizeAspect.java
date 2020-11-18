package com.sdrg.sell.aspect;

import com.sdrg.sell.Utils.CookieUtil;
import com.sdrg.sell.constant.CookieConstant;
import com.sdrg.sell.constant.RedisConstant;
import com.sdrg.sell.exception.SellerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
@Component
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.sdrg.sell.controller.Seller*.*(..))"
            + "&& !execution(public * com.sdrg.sell.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

//        try {
            Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
            //获取cookie
            if (cookie == null) {
                log.warn("【登陆校验】Cookie中查不到token");
                throw new SellerAuthorizeException();
            }
            //去Redis中查询token
            String tokenValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            if (StringUtils.isEmpty(tokenValue)){
                log.warn("【登陆校验】Redis中查不到token");
            }
//        }catch (Exception e){

//        }
//        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
//        if (cookie == null){
//            log.warn("【登陆校验】Cookie中查不到token");
//            throw new SellerAuthorizeException();

        //去Redis中查询token
//        String tokenValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
//        if (StringUtils.isEmpty(tokenValue)){
//            log.warn("【登陆校验】Redis中查不到token");
//            throw new SellerAuthorizeException();
//        }
    }
}
