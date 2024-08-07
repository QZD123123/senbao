package com.senbao.controller;


import com.senbao.pojo.Order;
import com.senbao.service.OrderService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public Result orderPageSelect(Integer page, Integer pageSize, @RequestParam(required = false) String progress){
        Result result = orderService.orderPageSelect(page,pageSize,progress);
        return result;
    }

    @PatchMapping("{id}")
    public Result UpdateOrderById(@PathVariable Integer id, @RequestBody Order order){
        Result result = orderService.UpdateOrderById(id,order);
        return result;
    }

    @PostMapping("")
    public Result createOrder(@RequestBody Order order){
        Result result = orderService.createOrder(order);
        return result;
    }

    @DeleteMapping("{id}")
    public Result deleteOrderById(@PathVariable Integer id){
        Result result = orderService.deleteOrderById(id);
        return result;
    }


}
