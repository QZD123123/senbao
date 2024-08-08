package com.senbao.service;

import com.senbao.pojo.Supply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.senbao.utils.Result;

/**
* @author ASUS
* @description 针对表【supply】的数据库操作Service
* @createDate 2024-08-07 17:00:55
*/
public interface SupplyService extends IService<Supply> {

    Result supplyPageSelect(Integer page, Integer pageSize, String materialId, String supplierId);

    Result addSupply(Supply supply);

    Result deleteSupply(Integer id);

    Result updateSupply(Integer id, Supply supply);
}
