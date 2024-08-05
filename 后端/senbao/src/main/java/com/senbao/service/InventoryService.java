package com.senbao.service;

import com.senbao.pojo.Inventory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.senbao.utils.Result;

/**
* @author ASUS
* @description 针对表【inventory】的数据库操作Service
* @createDate 2024-08-05 23:53:55
*/
public interface InventoryService extends IService<Inventory> {

    Result inventoryPageSelect(Integer page, Integer pageSize, String materialId, String warehouseId);

    Result addInventory(Inventory inventory);

    Result deleteInventory(Integer id);

    Result updateInventory(Integer id, Inventory inventory);
}
