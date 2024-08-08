package com.senbao.mapper;

import com.senbao.pojo.Consumption;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author ASUS
* @description 针对表【consumption】的数据库操作Mapper
* @createDate 2024-08-08 15:24:14
* @Entity com.senbao.pojo.Consumption
*/
public interface ConsumptionMapper extends BaseMapper<Consumption> {

    @Select("SELECT COUNT(*) FROM consumption WHERE materialId = #{materialId} AND warehouseId = #{warehouseId}")
    Long selectConsumptionCountByMaterialAndWarehouse(@Param("materialId") String materialId, @Param("warehouseId") String warehouseId);

    @Select("SELECT COUNT(*) FROM consumption WHERE materialId = #{materialId}")
    Long selectConsumptionCountByMaterial(@Param("materialId") String materialId);

    @Select("SELECT COUNT(*) FROM consumption WHERE warehouseId = #{warehouseId}")
    Long selectConsumptionCountByWarehouse(@Param("warehouseId") String warehouseId);

    @Select("SELECT COUNT(*) FROM consumption")
    Long selectConsumptionCount();

    @Select("SELECT * FROM consumption WHERE materialId = #{materialId} AND warehouseId = #{warehouseId} LIMIT #{offset}, #{pageSize}")
    List<Consumption> selectConsumptionPageByMaterialAndWarehouse(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("materialId") String materialId, @Param("warehouseId") String warehouseId);

    @Select("SELECT * FROM consumption WHERE materialId = #{materialId} LIMIT #{offset}, #{pageSize}")
    List<Consumption> selectConsumptionPageByMaterial(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("materialId") String materialId);

    @Select("SELECT * FROM consumption WHERE warehouseId = #{warehouseId} LIMIT #{offset}, #{pageSize}")
    List<Consumption> selectConsumptionPageByWarehouse(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("warehouseId") String warehouseId);

    @Select("SELECT * FROM consumption LIMIT #{offset}, #{pageSize}")
    List<Consumption> selectConsumptionPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int updateConsumptionById(@Param("id") Integer id, @Param("consumption") Consumption consumption);

    int insertConsumption( @Param("consumption") Consumption consumption);
}




