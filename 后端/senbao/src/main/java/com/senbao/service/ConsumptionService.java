package com.senbao.service;

import com.senbao.pojo.Consumption;
import com.baomidou.mybatisplus.extension.service.IService;
import com.senbao.utils.Result;

/**
* @author ASUS
* @description 针对表【consumption】的数据库操作Service
* @createDate 2024-08-08 15:24:14
*/
public interface ConsumptionService extends IService<Consumption> {

    Result addConsumption(Consumption consumption);

    Result updateConsumption(Integer id, Consumption consumption);

    Result deleteConsumption(Integer id);

    Result getConsumptionPage(Integer page, Integer pageSize, String materialId, String warehouseId);
}
