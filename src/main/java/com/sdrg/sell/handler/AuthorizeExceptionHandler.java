package com.sdrg.sell.handler;

import com.sdrg.sell.Utils.VOResultUtil;
import com.sdrg.sell.exception.SellException;
import com.sdrg.sell.exception.SellerAuthorizeException;
import com.sdrg.sell.vResult.VOResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthorizeExceptionHandler {

    @ExceptionHandler(value = SellerAuthorizeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String sellAuthorizeException(){
        return "未通过验证";
    }
//    public ModelAndView sellAuthorizeException(){
//        return new ModelAndView("redirect:http://127.0.0.1:8083/sell/seller/order/list");
//    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public VOResult orderException(SellException e){
        return VOResultUtil.error(e.getCode(),e.getMessage());
    }
}