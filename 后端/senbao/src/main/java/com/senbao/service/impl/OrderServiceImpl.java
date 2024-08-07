package com.senbao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.mapper.OrderMapper;
import com.senbao.pojo.Order;
import com.senbao.service.OrderService;
import com.senbao.utils.Result;
import com.senbao.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result orderPageSelect(Integer page, Integer pageSize, String progress) {
        int offset = (page - 1) * pageSize;

        // 根据进度筛选订单数量
        Long count = (progress != null && !progress.isEmpty())
                ? orderMapper.selectOrderCountByProgress(progress)
                : orderMapper.selectOrderCount();

        // 根据进度筛选分页查询订单列表
        List<Order> records = (progress != null && !progress.isEmpty())
                ? orderMapper.selectOrderPageByProgress(offset, pageSize, progress)
                : orderMapper.selectOrderPage(offset, pageSize);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "成功获取第" + page + "页，共" + records.size() + "条数据");
        data.put("page", page);
        data.put("count", pageSize);
        data.put("pageTotal", (int) Math.ceil((double) count / pageSize));
        data.put("orderTotal", count);
        data.put("orderList", records);

        return Result.ok(data);
    }


    @Override
    public Result UpdateOrderById(Integer id, Order order) {
        Map data = new LinkedHashMap();
        if (id == null || order == null) {
            data.put("tip","无效的参数");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
        try{
            int rowsAffected = orderMapper.updateOrderById(id,order);
            if (rowsAffected > 0) {
                data.put("tip","成功更新订单信息");
                return Result.ok(data);
            }else{
                data.put("tip","更新失败，订单可能不存在");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        }catch (Exception e){
            e.printStackTrace();
            data.put("tip","更新订单时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result createOrder(Order order) {
        Map data = new LinkedHashMap();
        try{
            int row = orderMapper.insertOrder(order);
            if (row > 0) {
                data.put("tip","订单添加成功");
                return Result.ok(data);
            }else{
                data.put("tip","添加订单失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        }catch (Exception e){
            e.printStackTrace();
            data.put("tip","添加订单时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result deleteOrderById(Integer id) {
        Map data = new LinkedHashMap();
        try{
            int row = orderMapper.deleteOrderById(id);
            if (row > 0) {
                data.put("tip","成功删除订单");
                return Result.ok(data);
            }else{
                data.put("tip","删除订单失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        }catch (Exception e){
            e.printStackTrace();
            data.put("tip","删除订单时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }



}
