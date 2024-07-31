package com.senbao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senbao.pojo.Order;
import com.senbao.utils.Result;

public interface OrderService extends IService<Order> {
    Result orderPageSelect(Integer page, Integer pageSize);

    Result UpdateOrderById(Integer id, Order order);

    Result createOrder(Order order);

    Result deleteOrderById(Integer id);
}
