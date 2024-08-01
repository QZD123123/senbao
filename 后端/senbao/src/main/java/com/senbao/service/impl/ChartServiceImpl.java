package com.senbao.service.impl;

import com.senbao.DTO.MonthlyTotalOrderPriceDTO;
import com.senbao.mapper.OrderMapper;
import com.senbao.service.ChartService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result MonthlyTotalOrderPrice() {
        // 从数据库获取数据
        List<Map<String, Object>> result = orderMapper.MonthlyTotalOrderPrice();

        // 创建 DTO 对象
        MonthlyTotalOrderPriceDTO dto = new MonthlyTotalOrderPriceDTO();

        List<String> months = new ArrayList<>();
        List<String> monthlyOrderTotals = new ArrayList<>();

        // 处理结果
        for (Map<String, Object> row : result) {
            String month = (String) row.get("month");  // 直接作为 String 处理
            String monthlyOrderTotal = row.get("monthlyOrderTotals").toString();  // 转为 String

            months.add(month);
            monthlyOrderTotals.add(monthlyOrderTotal);
        }

        Map data = new LinkedHashMap();
        data.put("months",months);
        data.put("monthlyOrderTotals",monthlyOrderTotals);
        // 返回结果
        return Result.ok(data);


    }
}
