package com.senbao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.mapper.InventoryMapper;
import com.senbao.pojo.Inventory;
import com.senbao.service.InventoryService;
import com.senbao.utils.Result;
import com.senbao.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public Result inventoryPageSelect(Integer page, Integer pageSize, String materialId, String warehouseId) {
        int offset = (page - 1) * pageSize;

        // 根据物料ID和仓库ID筛选库存数量
        Long count = (materialId != null && !materialId.isEmpty() && warehouseId != null && !warehouseId.isEmpty())
                ? inventoryMapper.selectInventoryCountByMaterialAndWarehouse(materialId, warehouseId)
                : (materialId != null && !materialId.isEmpty())
                ? inventoryMapper.selectInventoryCountByMaterial(materialId)
                : (warehouseId != null && !warehouseId.isEmpty())
                ? inventoryMapper.selectInventoryCountByWarehouse(warehouseId)
                : inventoryMapper.selectInventoryCount();

        // 根据物料ID和仓库ID筛选分页查询库存列表
        List<Inventory> records = (materialId != null && !materialId.isEmpty() && warehouseId != null && !warehouseId.isEmpty())
                ? inventoryMapper.selectInventoryPageByMaterialAndWarehouse(offset, pageSize, materialId, warehouseId)
                : (materialId != null && !materialId.isEmpty())
                ? inventoryMapper.selectInventoryPageByMaterial(offset, pageSize, materialId)
                : (warehouseId != null && !warehouseId.isEmpty())
                ? inventoryMapper.selectInventoryPageByWarehouse(offset, pageSize, warehouseId)
                : inventoryMapper.selectInventoryPage(offset, pageSize);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "成功获取第" + page + "页，共" + records.size() + "条数据");
        data.put("page", page);
        data.put("count", pageSize);
        data.put("pageTotal", (int) Math.ceil((double) count / pageSize));
        data.put("inventoryTotal", count);
        data.put("inventoryList", records);

        return Result.ok(data);
    }

    @Override
    public Result addInventory(Inventory inventory) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            int row = inventoryMapper.insert(inventory);
            if (row > 0) {
                data.put("tip", "库存添加成功");
                return Result.ok(data);
            } else {
                data.put("tip", "添加库存失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("tip", "添加库存时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result deleteInventory(Integer id) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            int row = inventoryMapper.deleteById(id);
            if (row > 0) {
                data.put("tip", "成功删除库存");
                return Result.ok(data);
            } else {
                data.put("tip", "删除库存失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("tip", "删除库存时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result updateInventory(Integer id, Inventory inventory) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (id == null || inventory == null) {
            data.put("tip", "无效的参数");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
        try {
            inventory.setId(id); // 确保更新时使用正确的ID
            int rowsAffected = inventoryMapper.updateInventoryById(id,inventory);
            if (rowsAffected > 0) {
                data.put("tip", "成功更新库存信息");
                return Result.ok(data);
            } else {
                data.put("tip", "更新失败，库存可能不存在");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("tip", "更新库存时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }
}
