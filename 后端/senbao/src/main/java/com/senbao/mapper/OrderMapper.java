package com.senbao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senbao.DTO.MonthlyTotalOrderPriceDTO;
import com.senbao.pojo.Order;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Order> {


    Long selectOrderCount();

    List<Order> selectOrderPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    int updateOrderById(@Param("id") Integer id, @Param("order") Order order);

    int insertOrder(@Param("TableOrder") Order order);

    int deleteOrderById(@Param("id") Integer id);

    @MapKey("month")  // 指定 Map 的键为 month
    List<Map<String, Object>> MonthlyTotalOrderPrice();
}
