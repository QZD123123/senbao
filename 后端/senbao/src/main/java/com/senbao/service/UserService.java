package com.senbao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senbao.pojo.User;
import com.senbao.utils.Result;

public interface UserService extends IService<User> {
    Result getAllUser();

    Result login(User user);
}
