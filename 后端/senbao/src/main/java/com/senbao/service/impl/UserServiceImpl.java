package com.senbao.service.impl;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senbao.mapper.UserMapper;
import com.senbao.pojo.User;
import com.senbao.service.UserService;
import com.senbao.utils.JwtHelper;
import com.senbao.utils.Result;
import com.senbao.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result getAllUser() {
        List<User> allUser = userMapper.getAllUser();

        return Result.ok(allUser);
    }

    @Override
    public Result login(User user) {
        Map data  =  new LinkedHashMap();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());

        User dbuser = userMapper.selectOne(queryWrapper);

        if(dbuser == null){
            data.put("tip","手机号与密码不匹配");
            return Result.build(data, ResultCodeEnum.no_Resource);
        }
        System.out.println(!StringUtils.isEmpty(user.getPassword()));
        System.out.println(user.getPassword().equals(dbuser.getPassword()));

        if (!StringUtils.isEmpty(user.getPassword()) && user.getPassword().equals(dbuser.getPassword())) {
            String token = jwtHelper.createToken(Long.valueOf(dbuser.getId()));


            data.put("tip","登陆成功");
            data.put("user",dbuser);
            data.put("token",token);

            return Result.ok(data);
        }
        data.put("tip","手机号与密码不匹配");
        return Result.build(data,ResultCodeEnum.Request_failed);

    }





}
