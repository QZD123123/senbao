package com.senbao.mapper;

import com.senbao.pojo.Material;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author ASUS
* @description 针对表【material】的数据库操作Mapper
* @createDate 2024-08-06 00:38:11
* @Entity com.senbao.pojo.Material
*/
public interface MaterialMapper extends BaseMapper<Material> {

    List<Material> getMaterial();

    // 查询总数量（按物料名称和类型）
    @Select("SELECT COUNT(*) FROM material WHERE type = #{type}")
    Long selectMaterialCountByType(@Param("type") String type);

    @Select("SELECT COUNT(*) FROM material")
    Long selectMaterialCount();

    @Select("SELECT * FROM material WHERE type = #{type} LIMIT #{offset}, #{pageSize}")
    List<Material> selectMaterialPageByType(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("type") String type);

    @Select("SELECT * FROM material LIMIT #{offset}, #{pageSize}")
    List<Material> selectMaterialPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int updateMaterialById(@Param("id") Integer id, @Param("material") Material material);
}




