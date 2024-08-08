package com.senbao.controller;

import com.senbao.pojo.Supplier;
import com.senbao.service.SupplierService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("")
    public Result supplierPageSelect(Integer page, Integer pageSize){
        Result result = supplierService.supplierPageSelect(page,pageSize);
        return result;
    }

    @PostMapping
    public Result createSupplier(@RequestBody Supplier supplier){
        Result result = supplierService.createSupplier(supplier);
        return result;
    }

    @PatchMapping("{id}")
    public Result UpdateSupplierById(@PathVariable Integer id, @RequestBody Supplier supplier){
        Result result = supplierService.UpdateSupplierById(id,supplier);
        return result;
    }

    @DeleteMapping("{id}")
    public Result deleteSupplierById(@PathVariable Integer id){
        Result result = supplierService.deleteSupplierById(id);
        return result;
    }



}
