package com.senbao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senbao.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {


    Long selectOrderCount();

    List<Order> selectOrderPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    int updateOrderById(@Param("id") Integer id, @Param("order") Order order);

    int insertOrder(@Param("TableOrder") Order order);

    int deleteOrderById(@Param("id") Integer id);
}
