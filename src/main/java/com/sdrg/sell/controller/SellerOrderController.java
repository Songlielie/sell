package com.sdrg.sell.controller;

import com.sdrg.sell.dataobject.OrderDetail;
import com.sdrg.sell.dto.OrderDTO;
import com.sdrg.sell.enums.OrderPayStatusEnum;
import com.sdrg.sell.enums.OrderStatusEnum;
import com.sdrg.sell.enums.ResultEnum;
import com.sdrg.sell.exception.SellException;
import com.sdrg.sell.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  Map<String, Object> map) {
        Page<OrderDTO> orderDTOPage = orderService.findAllList(PageRequest.of(page - 1, size));
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("pageSize", size);
        map.put("resultEnum", OrderStatusEnum.NEW.getMsg());
        return new ModelAndView("order/list", map);
    }

    /**
     * @Description: 查询订单详情
     * @Param:
     * @return:
     * @Author: Mr.Song
     * @Date: 2020-8-21
     */
    @GetMapping("/detail")
    public ModelAndView orderDetailList(@RequestParam(value = "orderId") String orderId,
                                        Map<String, Object> map) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        String orderStatusNew = OrderStatusEnum.NEW.getMsg();
        String orderNoPay = OrderPayStatusEnum.Wait.getMsg();
        map.put("orderDetailList", orderDetailList);
        map.put("orderId",orderId);
        map.put("orderDTO",orderDTO);
        map.put("orderStatusNew",orderStatusNew);
        map.put("orderNoPay",orderNoPay);
        return new ModelAndView("order/detail", map);
    }

    /**
     * @Description: 取消订单
     * @Param:
     * @return:
     * @Author: Mr.Song
     * @Date: 2020-8-21
     */
    @GetMapping(value = "/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * @Description: 完结订单
     * @Param:
     * @return:
     * @Author: Mr.Song
     * @Date: 2020-8-21
     */
    @GetMapping(value = "/finish")
    public ModelAndView finish(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
