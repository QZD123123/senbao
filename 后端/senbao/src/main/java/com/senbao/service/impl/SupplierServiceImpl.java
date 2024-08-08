package com.senbao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.pojo.Supplier;
import com.senbao.service.SupplierService;
import com.senbao.mapper.SupplierMapper;
import com.senbao.utils.Result;
import com.senbao.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author ASUS
* @description 针对表【supplier】的数据库操作Service实现
* @createDate 2024-08-07 15:48:10
*/
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier>
    implements SupplierService{

    @Autowired
    private SupplierMapper supplierMapper;
    @Override
    public Result supplierPageSelect(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;

        Long count = supplierMapper.selectSupplierCount();

        List<Supplier> records = supplierMapper.selectSupplierPage(offset,pageSize);

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页，共"+records.size()+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("supplierTotal",count);
        data.put("supplierList",records);

        return Result.ok(data);
    }

    @Override
    public Result createSupplier(Supplier supplier) {
        Map data = new LinkedHashMap();
        try{
            int row = supplierMapper.insertSupplier(supplier);
            if (row > 0) {
                data.put("tip","供应商添加成功");
                return Result.ok(data);
            }else{
                data.put("tip","添加供应商失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        }catch (Exception e){
            e.printStackTrace();
            data.put("tip","添加供应商时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result UpdateSupplierById(Integer id, Supplier supplier) {
        Map data = new LinkedHashMap();
        if (id == null || supplier == null) {
            data.put("tip","无效的参数");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
        try{
            int rowsAffected = supplierMapper.UpdateSupplierById(id,supplier);
            if (rowsAffected > 0) {
                data.put("tip","成功更新供应商信息");
                return Result.ok(data);
            }else{
                data.put("tip","更新失败，供应商可能不存在");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        }catch (Exception e){
            e.printStackTrace();
            data.put("tip","更新供应商时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }

    @Override
    public Result deleteSupplierById(Integer id) {
        Map data = new LinkedHashMap();
        try{
            int row = supplierMapper.deleteSupplierById(id);
            if (row > 0) {
                data.put("tip","成功删除供应商");
                return Result.ok(data);
            }else{
                data.put("tip","删除供应商失败");
                return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
            }
        }catch (Exception e){
            e.printStackTrace();
            data.put("tip","删除供应商时发生错误");
            return Result.build(data, ResultCodeEnum.requested_resource_no_modified);
        }
    }
}




