package com.senbao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senbao.pojo.Order;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Order> {

    // 根据进度获取订单总数
    Long selectOrderCountByProgress(@Param("progress") String progress);

    // 根据进度分页查询订单列表
    List<Order> selectOrderPageByProgress(@Param("offset") int offset, @Param("limit") int limit, @Param("progress") String progress);

    // 获取所有订单总数
    Long selectOrderCount();

    // 分页查询订单列表
    List<Order> selectOrderPage(@Param("offset") int offset, @Param("limit") int limit);


    int updateOrderById(@Param("id") Integer id, @Param("order") Order order);

    int insertOrder(@Param("TableOrder") Order order);

    int deleteOrderById(@Param("id") Integer id);

    @MapKey("month")  // 指定 Map 的键为 month
    List<Map<String, Object>> MonthlyTotalOrderPrice();

    String TotalOrderPriceOfYear();

    String CountOrderOfYear();

    String CountCustomer();
}
