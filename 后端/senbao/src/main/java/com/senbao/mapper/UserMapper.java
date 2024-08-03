package com.senbao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senbao.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {


    User findByPhone(@Param("phone") String phone);


    // 获取所有员工的总数
    Long selectUserCount();

    // 根据职位获取员工的总数
    Long selectUserCountByRole(@Param("role") String role);

    // 分页查询员工列表
    List<User> selectUserPage(@Param("offset") int offset, @Param("limit") int limit);

    // 根据职位分页查询员工列表
    List<User> selectUserPageByRole(@Param("offset") int offset, @Param("limit") int limit, @Param("role") String role);
}
