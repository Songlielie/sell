package com.sdrg.sell.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    /**
     *@Description: 设置cookie
     *@Author: Mr.Song
     *@Date: 2020-9-7
    */
    public static void set(String name, String value, int maxAge, HttpServletResponse response){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     *@Description: 通过cookie name查看 cookie是否存在
     *@Author: Mr.Song
     *@Date: 2020-9-7
    */
    public static Cookie get(HttpServletRequest request, String name){
        Map<String,Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }
        else
            return null;
    }

    /**
     *@Description: 通过HttpServletRequest获取cookies，存放到Map中
     *@Author: Mr.Song
     *@Date: 2020-9-7
    */
    public static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie !=null){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}