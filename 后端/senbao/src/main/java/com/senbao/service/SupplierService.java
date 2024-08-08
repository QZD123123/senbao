package com.senbao.service;

import com.senbao.pojo.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.senbao.utils.Result;

/**
* @author ASUS
* @description 针对表【supplier】的数据库操作Service
* @createDate 2024-08-07 15:48:10
*/
public interface SupplierService extends IService<Supplier> {

    Result supplierPageSelect(Integer page, Integer pageSize);

    Result createSupplier(Supplier supplier);

    Result UpdateSupplierById(Integer id, Supplier supplier);

    Result deleteSupplierById(Integer id);

    Result getAll();
}
