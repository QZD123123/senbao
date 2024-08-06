package com.senbao.service;

import com.senbao.pojo.Material;
import com.baomidou.mybatisplus.extension.service.IService;
import com.senbao.utils.Result;

/**
* @author ASUS
* @description 针对表【material】的数据库操作Service
* @createDate 2024-08-06 00:38:11
*/
public interface MaterialService extends IService<Material> {

    Result getMaterial();

    Result addMaterial(Material material);

    Result deleteMaterial(Integer id);

    Result updateMaterial(Integer id, Material material);

    Result materialPageSelect(Integer page, Integer pageSize, String type);
}
