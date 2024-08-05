package com.senbao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.pojo.Material;
import com.senbao.service.MaterialService;
import com.senbao.mapper.MaterialMapper;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author ASUS
* @description 针对表【material】的数据库操作Service实现
* @createDate 2024-08-06 00:38:11
*/
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material>
    implements MaterialService{

    @Autowired
    private MaterialMapper materialMapper;
    @Override
    public Result getMaterial() {
        List<Material> list = materialMapper.getMaterial();

        Map data = new LinkedHashMap();
        data.put("list",list);
        return Result.ok(data);
    }
}




