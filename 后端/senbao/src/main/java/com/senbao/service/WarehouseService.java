package com.senbao.service;

import com.senbao.pojo.Warehouse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.senbao.utils.Result;

/**
* @author ASUS
* @description 针对表【warehouse】的数据库操作Service
* @createDate 2024-08-03 22:59:10
*/
public interface WarehouseService extends IService<Warehouse> {

    Result warehousePageSelect(Integer page, Integer pageSize);
}
