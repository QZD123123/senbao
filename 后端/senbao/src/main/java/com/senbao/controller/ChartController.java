package com.senbao.controller;

import com.senbao.service.ChartService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("chart")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/monthly")
    public Result MonthlyTotalOrderPrice(){
        Result result = chartService.MonthlyTotalOrderPrice();
        return result;
    }

    @GetMapping("/dashboard")
    public Result OrderCustomerSupplier(){
        Result result = chartService.OrderCustomerSupplier();
        return result;
    }

}
