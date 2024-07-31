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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
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

    @Override
    public Result register(User user) {

        Map data = new LinkedHashMap();

        User dbUser = userMapper.findByPhone(user.getPhone());
        if (dbUser == null) {
            int rows = userMapper.insert(user);
            System.out.println("rows = " + rows);
            System.out.println("user = " + user);
            data.put("tip","注册成功");
        }else {
            data.put("tip","账号已存在");
            return Result.build(data,ResultCodeEnum.requested_resource_no_modified);
        }
        return Result.ok(data);
    }

    @Override
    public Result userPageSelect(Integer page, Integer pageSize) {
        //获取员工总数
        Long count = userMapper.selectUserCount();

        //分页查询订单列表
        List<User> records = userMapper.selectUserPage((page - 1) * pageSize, pageSize);

//        ArrayList<User> list = new ArrayList<>();

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页，共"+records.size()+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("userTotal",count);
        data.put("userList",records);

        return Result.ok(data);
    }

    @Override
    public Result selectUserById(Integer id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        User user = userMapper.selectOne(queryWrapper);

        Map data = new LinkedHashMap();
        data.put("tip","成功获取指定用户");
        data.put("user",user);

        return Result.ok(data);
    }

    @Override
    public Result updateUserById(Integer id, User user) {

        user.setId(id);
        userMapper.updateById(user);

        Map data = new LinkedHashMap();
        data.put("tip","成功更新用户信息");

        return Result.ok(data);
    }

    @Override
    public Result deleteUser(Integer id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        int i = userMapper.delete(queryWrapper);

        Map data = new LinkedHashMap();

        if (i==0) {
            data.put("tip","删除用户失败");
        }
        if(i>0){
            System.out.println("删除用户"+id+"成功");
            data.put("tip","成功删除用户");
        }
        return Result.ok(data);
    }




}
