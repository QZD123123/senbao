package com.senbao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.mapper.ConsumptionMapper;
import com.senbao.pojo.Consumption;
import com.senbao.service.ConsumptionService;
import com.senbao.utils.Result;
import com.senbao.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsumptionServiceImpl extends ServiceImpl<ConsumptionMapper, Consumption> implements ConsumptionService {

    @Autowired
    private ConsumptionMapper consumptionMapper;

    @Override
    public Result getConsumptionPage(Integer page, Integer pageSize, String materialId, String warehouseId) {
        int offset = (page - 1) * pageSize;

        // 获取消耗记录分页数据
        Long count = (materialId != null && !materialId.isEmpty() && warehouseId != null && !warehouseId.isEmpty())
                ? consumptionMapper.selectConsumptionCountByMaterialAndWarehouse(materialId, warehouseId)
                : (materialId != null && !materialId.isEmpty())
                ? consumptionMapper.selectConsumptionCountByMaterial(materialId)
                : (warehouseId != null && !warehouseId.isEmpty())
                ? consumptionMapper.selectConsumptionCountByWarehouse(warehouseId)
                : consumptionMapper.selectConsumptionCount();

        List<Consumption> records = (materialId != null && !materialId.isEmpty() && warehouseId != null && !warehouseId.isEmpty())
                ? consumptionMapper.selectConsumptionPageByMaterialAndWarehouse(offset, pageSize, materialId, warehouseId)
                : (materialId != null && !materialId.isEmpty())
                ? consumptionMapper.selectConsumptionPageByMaterial(offset, pageSize, materialId)
                : (warehouseId != null && !warehouseId.isEmpty())
                ? consumptionMapper.selectConsumptionPageByWarehouse(offset, pageSize, warehouseId)
                : consumptionMapper.selectConsumptionPage(offset, pageSize);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "成功获取第" + page + "页，共" + records.size() + "条数据");
        data.put("page", page);
        data.put("count", pageSize);
        data.put("pageTotal", (int) Math.ceil((double) count / pageSize));
        data.put("consumptionTotal", count);
        data.put("consumptionList", records);

        return Result.ok(data);
    }

    @Override
    public Result addConsumption(Consumption consumption) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            int row = consumptionMapper.insertConsumption(consumption);
            if (row > 0) {
                data.put("tip", "消耗记录添加成功");
                return Result.ok(data);
            } else {
                data.put("tip", "添加消耗记录失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("tip", "添加消耗记录时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result updateConsumption(Integer id, Consumption consumption) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (id == null || consumption == null) {
            data.put("tip", "无效的参数");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
        try {
            consumption.setId(id);
            int rowsAffected = consumptionMapper.updateConsumptionById(id, consumption);
            if (rowsAffected > 0) {
                data.put("tip", "成功更新消耗记录");
                return Result.ok(data);
            } else {
                data.put("tip", "更新失败，消耗记录可能不存在");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("tip", "更新消耗记录时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result deleteConsumption(Integer id) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            int row = consumptionMapper.deleteById(id);
            if (row > 0) {
                data.put("tip", "成功删除消耗记录");
                return Result.ok(data);
            } else {
                data.put("tip", "删除消耗记录失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("tip", "删除消耗记录时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

//    @Override
//    public Result getConsumptionById(Integer id) {
//        Map<String, Object> data = new LinkedHashMap<>();
//        try {
//            Consumption consumption = consumptionMapper.selectById(id);
//            if (consumption != null) {
//                data.put("data", consumption);
//                return Result.ok(data);
//            } else {
//                data.put("tip", "消耗记录未找到");
//                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            data.put("tip", "获取消耗记录时发生错误");
//            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
//        }
//    }

//    @Override
//    public Result getAllConsumptions() {
//        Map<String, Object> data = new LinkedHashMap<>();
//        try {
//            List<Consumption> consumptions = consumptionMapper.selectAll();
//            data.put("data", consumptions);
//            return Result.ok(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//            data.put("tip", "获取所有消耗记录时发生错误");
//            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
//        }
//    }
}
