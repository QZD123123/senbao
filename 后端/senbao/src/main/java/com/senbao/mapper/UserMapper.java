package com.senbao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senbao.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {


    User findByPhone(@Param("phone") String phone);


    Long selectUserCount();

    List<User> selectUserPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

}
