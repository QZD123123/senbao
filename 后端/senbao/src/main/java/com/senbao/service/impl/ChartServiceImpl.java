package com.senbao.service.impl;

import com.senbao.mapper.OrderMapper;
import com.senbao.mapper.SupplierMapper;
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

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public Result MonthlyTotalOrderPrice() {
        // 从数据库获取数据
        List<Map<String, Object>> result = orderMapper.MonthlyTotalOrderPrice();

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

    @Override
    public Result OrderCustomerSupplier() {
        String sales = orderMapper.TotalOrderPriceOfYear();
        String orders = orderMapper.CountOrderOfYear();
        String customers = orderMapper.CountCustomer();
        long suppliers = supplierMapper.selectSupplierCount();

        Map data = new LinkedHashMap();
        data.put("sales",sales);
        data.put("orders",orders);
        data.put("customers",customers);
        data.put("suppliers",suppliers);

        return Result.ok(data);

    }

}
