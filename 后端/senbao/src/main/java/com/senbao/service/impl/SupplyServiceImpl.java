package com.senbao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.mapper.SupplyMapper;
import com.senbao.pojo.Supply;
import com.senbao.service.SupplyService;
import com.senbao.utils.Result;
import com.senbao.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 针对表【supply】的数据库操作Service实现
 * @createDate 2024-08-07 17:00:55
 */
@Service
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply> implements SupplyService {

    @Autowired
    private SupplyMapper supplyMapper;

    @Override
    public Result supplyPageSelect(Integer page, Integer pageSize, String materialId, String supplierId) {
        int offset = (page - 1) * pageSize;

        Long count;
        List<Supply> records;

        // 根据物料ID和供应商ID筛选记录
        if (materialId != null && !materialId.isEmpty() && supplierId != null && !supplierId.isEmpty()) {
            count = supplyMapper.selectSupplyCountByMaterialAndSupplier(materialId, supplierId);
            records = supplyMapper.selectSupplyPageByMaterialAndSupplier(offset, pageSize, materialId, supplierId);
        } else if (materialId != null && !materialId.isEmpty()) {
            count = supplyMapper.selectSupplyCountByMaterial(materialId);
            records = supplyMapper.selectSupplyPageByMaterial(offset, pageSize, materialId);
        } else if (supplierId != null && !supplierId.isEmpty()) {
            count = supplyMapper.selectSupplyCountBySupplier(supplierId);
            records = supplyMapper.selectSupplyPageBySupplier(offset, pageSize, supplierId);
        } else {
            count = supplyMapper.selectSupplyCount();
            records = supplyMapper.selectSupplyPage(offset, pageSize);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "成功获取第" + page + "页，共" + records.size() + "条数据");
        data.put("page", page);
        data.put("count", pageSize);
        data.put("pageTotal", (int) Math.ceil((double) count / pageSize));
        data.put("supplyTotal", count);
        data.put("supplyList", records);

        return Result.ok(data);
    }

    @Override
    public Result addSupply(Supply supply) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            int row = supplyMapper.insert(supply);
            if (row > 0) {
                data.put("tip", "供应记录添加成功");
                return Result.ok(data);
            } else {
                data.put("tip", "添加供应记录失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 使用日志框架记录异常信息
            data.put("tip", "添加供应记录时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result deleteSupply(Integer id) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            int row = supplyMapper.deleteById(id);
            if (row > 0) {
                data.put("tip", "成功删除供应记录");
                return Result.ok(data);
            } else {
                data.put("tip", "删除供应记录失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 使用日志框架记录异常信息
            data.put("tip", "删除供应记录时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result updateSupply(Integer id, Supply supply) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (id == null || supply == null) {
            data.put("tip", "无效的参数");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
        try {
            supply.setId(id); // 确保更新时使用正确的ID
            int rowsAffected = supplyMapper.updateSupplyById(id, supply);
            if (rowsAffected > 0) {
                data.put("tip", "成功更新供应记录信息");
                return Result.ok(data);
            } else {
                data.put("tip", "更新失败，供应记录可能不存在");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 使用日志框架记录异常信息
            data.put("tip", "更新供应记录时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }
}
