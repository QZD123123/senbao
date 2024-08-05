package com.senbao.mapper;

import com.senbao.pojo.Inventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ASUS
* @description 针对表【inventory】的数据库操作Mapper
* @createDate 2024-08-05 23:53:55
* @Entity com.senbao.pojo.Inventory
*/
public interface InventoryMapper extends BaseMapper<Inventory> {

    Long selectInventoryCountByMaterialAndWarehouse(@Param("materialId") String materialId, @Param("warehouseId") String warehouseId);

    Long selectInventoryCountByMaterial(@Param("materialId") String materialId);

    Long selectInventoryCountByWarehouse(@Param("warehouseId") String warehouseId);

    Long selectInventoryCount();

    List<Inventory> selectInventoryPageByMaterialAndWarehouse(@Param("offset") int offset, @Param("pageSize") Integer pageSize,@Param("materialId") String materialId, @Param("warehouseId") String warehouseId);

    List<Inventory> selectInventoryPageByMaterial(@Param("offset") int offset, @Param("pageSize") Integer pageSize, @Param("materialId") String materialId);

    List<Inventory> selectInventoryPageByWarehouse( @Param("offset") int offset, @Param("pageSize") Integer pageSize, @Param("warehouseId") String warehouseId);

    List<Inventory> selectInventoryPage( @Param("offset") int offset, @Param("pageSize") Integer pageSize);

    int updateInventoryById(@Param("id") Integer id, @Param("inventory") Inventory inventory);
}




