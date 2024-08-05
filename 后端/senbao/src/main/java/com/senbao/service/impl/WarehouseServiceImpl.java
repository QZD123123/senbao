package com.senbao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.pojo.Warehouse;
import com.senbao.response.warehouseManagerRSP;
import com.senbao.response.warehouseRSP;
import com.senbao.service.WarehouseService;
import com.senbao.mapper.WarehouseMapper;
import com.senbao.utils.Result;
import com.senbao.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author ASUS
* @description 针对表【warehouse】的数据库操作Service实现
* @createDate 2024-08-03 22:59:10
*/
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse>
    implements WarehouseService{

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public Result warehousePageSelect(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;

        Long count = warehouseMapper.selectWarehouseCount();

        List<warehouseRSP> records = warehouseMapper.selectWarehousePage(offset,pageSize);

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页，共"+records.size()+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("warehouseTotal",count);
        data.put("warehouseList",records);

        return Result.ok(data);

    }

    @Override
    public Result selectWarehouseManager() {
        List<warehouseManagerRSP> adminList = warehouseMapper.selectWarehouseManager();

        Map data = new LinkedHashMap();
        data.put("adminList",adminList);

        return Result.ok(data);
    }

    @Override
    public Result createWarehouse(Warehouse warehouse) {
        Map data = new LinkedHashMap();
        try{
            int row = warehouseMapper.insertWarehouse(warehouse);
            if (row > 0) {
                data.put("tip","仓库添加成功");
                return Result.ok(data);
            }else{
                data.put("tip","仓库订单失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        }catch (Exception e){
            e.printStackTrace();
            data.put("tip","添加仓库时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result UpdateWarehouseById(Integer id, Warehouse warehouse) {
        Map data = new LinkedHashMap();
        if (id == null || warehouse == null) {
            data.put("tip","无效的参数");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
        try{
            int rowsAffected = warehouseMapper.UpdateWarehouseById(id,warehouse);
            if (rowsAffected > 0) {
                data.put("tip","成功更新仓库信息");
                return Result.ok(data);
            }else{
                data.put("tip","更新失败，仓库可能不存在");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        }catch (Exception e){
            e.printStackTrace();
            data.put("tip","更新仓库时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result deleteWarehouseById(Integer id) {
        Map data = new LinkedHashMap();
        try{
            int row = warehouseMapper.deleteWarehouseById(id);
            if (row > 0) {
                data.put("tip","成功删除仓库");
                return Result.ok(data);
            }else{
                data.put("tip","删除仓库失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        }catch (Exception e){
            e.printStackTrace();
            data.put("tip","删除仓库时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result getAll() {
        List<Warehouse> list = warehouseMapper.getAll();

        Map data = new LinkedHashMap();
        data.put("list",list);
        return Result.ok(data);
    }


}




