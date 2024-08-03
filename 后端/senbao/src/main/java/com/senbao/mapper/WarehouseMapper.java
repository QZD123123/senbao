package com.senbao.mapper;

import com.senbao.pojo.Warehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senbao.response.warehouseRSP;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ASUS
* @description 针对表【warehouse】的数据库操作Mapper
* @createDate 2024-08-03 22:59:10
* @Entity com.senbao.pojo.Warehouse
*/
public interface WarehouseMapper extends BaseMapper<Warehouse> {

    Long selectWarehouseCount();

    List<warehouseRSP> selectWarehousePage(@Param("offset") int offset, @Param("pageSize") Integer pageSize);
}




