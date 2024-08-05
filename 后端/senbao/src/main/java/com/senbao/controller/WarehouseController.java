package com.senbao.controller;

import com.senbao.pojo.Order;
import com.senbao.pojo.Warehouse;
import com.senbao.service.WarehouseService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Result createWarehouse(@RequestBody Warehouse warehouse){
        Result result = warehouseService.createWarehouse(warehouse);
        return result;
    }

    @GetMapping("manager")
    public Result selectWarehouseManager(){
        Result result = warehouseService.selectWarehouseManager();
        return result;
    }

    @PatchMapping("{id}")
    public Result UpdateWarehouseById(@PathVariable Integer id, @RequestBody Warehouse warehouse){
        Result result = warehouseService.UpdateWarehouseById(id,warehouse);
        return result;
    }

    @DeleteMapping("{id}")
    public Result deleteWarehouseById(@PathVariable Integer id){
        Result result = warehouseService.deleteWarehouseById(id);
        return result;
    }

    @GetMapping("getall")
    public Result getAll(){
        Result result = warehouseService.getAll();
        return result;
    }

}
