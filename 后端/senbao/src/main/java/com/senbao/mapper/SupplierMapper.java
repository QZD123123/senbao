package com.senbao.mapper;

import com.senbao.pojo.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author ASUS
* @description 针对表【supplier】的数据库操作Mapper
* @createDate 2024-08-01 22:28:58
* @Entity com.senbao.pojo.Supplier
*/
public interface SupplierMapper extends BaseMapper<Supplier> {

    String CountSupplier();
}




