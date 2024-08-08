package com.senbao.mapper;

import com.senbao.pojo.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senbao.pojo.Warehouse;
import com.senbao.response.warehouseRSP;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ASUS
* @description 针对表【supplier】的数据库操作Mapper
* @createDate 2024-08-07 15:48:10
* @Entity com.senbao.pojo.Supplier
*/
public interface SupplierMapper extends BaseMapper<Supplier> {

    Long selectSupplierCount();

    List<Supplier> selectSupplierPage(int offset, Integer pageSize);

    int insertSupplier(@Param("TableSupplier") Supplier TableSupplier);

    int UpdateSupplierById(@Param("id") Integer id,@Param("supplier") Supplier supplier);

    int deleteSupplierById(@Param("id") Integer id);
}




