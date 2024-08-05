package com.senbao.mapper;

import com.senbao.pojo.Material;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author ASUS
* @description 针对表【material】的数据库操作Mapper
* @createDate 2024-08-06 00:38:11
* @Entity com.senbao.pojo.Material
*/
public interface MaterialMapper extends BaseMapper<Material> {

    List<Material> getMaterial();
}




