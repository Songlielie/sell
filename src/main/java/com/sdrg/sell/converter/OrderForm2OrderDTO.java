package com.sdrg.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdrg.sell.dataobject.OrderDetail;
import com.sdrg.sell.dto.OrderDTO;
import com.sdrg.sell.enums.ResultEnum;
import com.sdrg.sell.exception.SellException;
import com.sdrg.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTO {

    public static OrderDTO convertOrderDTO(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderForm, orderDTO);

        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            log.error("[对象转换]错误,orderDetailList->{}", orderDetailList);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
