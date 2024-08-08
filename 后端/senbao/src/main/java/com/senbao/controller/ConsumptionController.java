package com.senbao.controller;

import com.senbao.pojo.Consumption;
import com.senbao.service.ConsumptionService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("consumption")
public class ConsumptionController {

    @Autowired
    private ConsumptionService consumptionService;

    // 添加消耗记录
    @PostMapping
    public Result addConsumption(@RequestBody Consumption consumption) {
        return consumptionService.addConsumption(consumption);
    }

    // 更新消耗记录
    @PatchMapping("/{id}")
    public Result updateConsumption(@PathVariable Integer id, @RequestBody Consumption consumption) {
        return consumptionService.updateConsumption(id, consumption);
    }

    // 删除消耗记录
    @DeleteMapping("/{id}")
    public Result deleteConsumption(@PathVariable Integer id) {
        return consumptionService.deleteConsumption(id);
    }

    // 获取消耗记录分页数据
    @GetMapping
    public Result getConsumptionPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String materialId,
            @RequestParam(required = false) String warehouseId) {
        return consumptionService.getConsumptionPage(page, pageSize, materialId, warehouseId);
    }
}
