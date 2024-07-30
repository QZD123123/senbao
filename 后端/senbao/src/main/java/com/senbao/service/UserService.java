package com.senbao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senbao.pojo.User;
import com.senbao.utils.Result;

public interface UserService extends IService<User> {


    Result login(User user);

    Result register(User user);

    Result userPageSelect(Integer page, Integer pageSize);

    Result selectUserById(Integer id);

    Result updateUserById(Integer id, User user);

    Result deleteUser(Integer id);

}
