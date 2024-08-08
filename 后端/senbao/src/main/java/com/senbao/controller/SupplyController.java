package com.senbao.controller;

import com.senbao.pojo.Supply;
import com.senbao.service.SupplyService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("supply")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @GetMapping
    public Result supplyPageSelect(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String materialId,
            @RequestParam(required = false) String supplierId
    ) {
        Result result = supplyService.supplyPageSelect(page, pageSize, materialId, supplierId);
        return result;
    }

    // 添加库存
    @PostMapping
    public Result addSupply(@RequestBody Supply supply) {
        Result result = supplyService.addSupply(supply);
        return result;
    }

    // 删除库存
    @DeleteMapping("/{id}")
    public Result deleteSupply(@PathVariable Integer id) {
        Result result = supplyService.deleteSupply(id);
        return result;
    }

    // 更新库存信息
    @PatchMapping("/{id}")
    public Result updateSupply(
            @PathVariable Integer id,
            @RequestBody Supply supply
    ) {
        Result result = supplyService.updateSupply(id, supply);
        return result;
    }

}
