package com.senbao.controller;

import com.senbao.service.WarehouseService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("")
    public Result warehousePageSelect(Integer page, Integer pageSize){
        Result result = warehouseService.warehousePageSelect(page,pageSize);
        return result;
    }


}
