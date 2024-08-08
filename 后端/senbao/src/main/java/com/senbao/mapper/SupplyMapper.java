package com.senbao.mapper;

import com.senbao.pojo.Supply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ASUS
* @description 针对表【supply】的数据库操作Mapper
* @createDate 2024-08-07 17:00:55
* @Entity com.senbao.pojo.Supply
*/
public interface SupplyMapper extends BaseMapper<Supply> {
    /**
     * 根据物料ID和供应商ID计算供应记录总数
     * @param materialId 物料ID
     * @param supplierId 供应商ID
     * @return 供应记录总数
     */
    Long selectSupplyCountByMaterialAndSupplier(@Param("materialId") String materialId, @Param("supplierId") String supplierId);

    /**
     * 根据物料ID计算供应记录总数
     * @param materialId 物料ID
     * @return 供应记录总数
     */
    Long selectSupplyCountByMaterial(@Param("materialId") String materialId);

    /**
     * 根据供应商ID计算供应记录总数
     * @param supplierId 供应商ID
     * @return 供应记录总数
     */
    Long selectSupplyCountBySupplier(@Param("supplierId") String supplierId);

    /**
     * 计算所有供应记录总数
     * @return 供应记录总数
     */
    Long selectSupplyCount();

    /**
     * 根据物料ID和供应商ID进行分页查询
     * @param offset 偏移量
     * @param pageSize 页大小
     * @param materialId 物料ID
     * @param supplierId 供应商ID
     * @return 供应记录列表
     */
    List<Supply> selectSupplyPageByMaterialAndSupplier(@Param("offset") int offset, @Param("pageSize") Integer pageSize, @Param("materialId") String materialId, @Param("supplierId") String supplierId);

    /**
     * 根据物料ID进行分页查询
     * @param offset 偏移量
     * @param pageSize 页大小
     * @param materialId 物料ID
     * @return 供应记录列表
     */
    List<Supply> selectSupplyPageByMaterial(@Param("offset") int offset, @Param("pageSize") Integer pageSize, @Param("materialId") String materialId);

    /**
     * 根据供应商ID进行分页查询
     * @param offset 偏移量
     * @param pageSize 页大小
     * @param supplierId 供应商ID
     * @return 供应记录列表
     */
    List<Supply> selectSupplyPageBySupplier(@Param("offset") int offset, @Param("pageSize") Integer pageSize, @Param("supplierId") String supplierId);

    /**
     * 查询所有供应记录的分页数据
     * @param offset 偏移量
     * @param pageSize 页大小
     * @return 供应记录列表
     */
    List<Supply> selectSupplyPage(@Param("offset") int offset, @Param("pageSize") Integer pageSize);

    /**
     * 根据ID更新供应记录
     * @param id 供应记录ID
     * @param supply 供应记录
     * @return 更新操作的行数
     */
    int updateSupplyById(@Param("id") Integer id, @Param("supply") Supply supply);

    int addSupply(@Param("supply") Supply supply);
}




