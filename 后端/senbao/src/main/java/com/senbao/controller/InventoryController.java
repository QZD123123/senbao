package com.senbao.controller;

import com.senbao.pojo.Inventory;
import com.senbao.service.InventoryService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public Result inventoryPageSelect(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String materialId,
            @RequestParam(required = false) String warehouseId
    ) {
        Result result = inventoryService.inventoryPageSelect(page, pageSize, materialId, warehouseId);
        return result;
    }

    // 添加库存
    @PostMapping
    public Result addInventory(@RequestBody Inventory inventory) {
        Result result = inventoryService.addInventory(inventory);
        return result;
    }

    // 删除库存
    @DeleteMapping("/{id}")
    public Result deleteInventory(@PathVariable Integer id) {
        Result result = inventoryService.deleteInventory(id);
        return result;
    }

    // 更新库存信息
    @PatchMapping("/{id}")
    public Result updateInventory(
            @PathVariable Integer id,
            @RequestBody Inventory inventory
    ) {
        Result result = inventoryService.updateInventory(id, inventory);
        return result;
    }



}
