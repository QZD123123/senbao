package com.senbao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.pojo.Material;
import com.senbao.service.MaterialService;
import com.senbao.mapper.MaterialMapper;
import com.senbao.utils.Result;
import com.senbao.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author ASUS
* @description 针对表【material】的数据库操作Service实现
* @createDate 2024-08-06 00:38:11
*/
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material>
    implements MaterialService{

    @Autowired
    private MaterialMapper materialMapper;
    @Override
    public Result getMaterial() {
        List<Material> list = materialMapper.getMaterial();

        Map data = new LinkedHashMap();
        data.put("list",list);
        return Result.ok(data);
    }


    @Override
    public Result materialPageSelect(Integer page, Integer pageSize, String type) {
        int offset = (page - 1) * pageSize;

        // 根据物料类型筛选物料数量
        Long count = (type != null && !type.isEmpty())
                ? materialMapper.selectMaterialCountByType(type)
                : materialMapper.selectMaterialCount();

        // 根据物料类型筛选分页查询物料列表
        List<Material> records = (type != null && !type.isEmpty())
                ? materialMapper.selectMaterialPageByType(offset, pageSize, type)
                : materialMapper.selectMaterialPage(offset, pageSize);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "成功获取第" + page + "页，共" + records.size() + "条数据");
        data.put("page", page);
        data.put("count", pageSize);
        data.put("pageTotal", (int) Math.ceil((double) count / pageSize));
        data.put("materialTotal", count);
        data.put("materialList", records);

        return Result.ok(data);
    }

    @Override
    public Result addMaterial(Material material) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            int row = materialMapper.insert(material);
            if (row > 0) {
                data.put("tip", "物料添加成功");
                return Result.ok(data);
            } else {
                data.put("tip", "添加物料失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("tip", "添加物料时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result deleteMaterial(Integer id) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            int row = materialMapper.deleteById(id);
            if (row > 0) {
                data.put("tip", "成功删除物料");
                return Result.ok(data);
            } else {
                data.put("tip", "删除物料失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("tip", "删除物料时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result updateMaterial(Integer id, Material material) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (id == null || material == null) {
            data.put("tip", "无效的参数");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
        try {
            material.setId(id); // 确保更新时使用正确的ID
            int rowsAffected = materialMapper.updateMaterialById(id, material);
            if (rowsAffected > 0) {
                data.put("tip", "成功更新物料信息");
                return Result.ok(data);
            } else {
                data.put("tip", "更新失败，物料可能不存在");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.put("tip", "更新物料时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }
}




