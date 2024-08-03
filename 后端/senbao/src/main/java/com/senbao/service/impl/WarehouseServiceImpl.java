package com.senbao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.pojo.Warehouse;
import com.senbao.response.warehouseRSP;
import com.senbao.service.WarehouseService;
import com.senbao.mapper.WarehouseMapper;
import com.senbao.utils.Result;
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
        data.put("orderTotal",count);
        data.put("orderList",records);

        return Result.ok(data);

    }
}




