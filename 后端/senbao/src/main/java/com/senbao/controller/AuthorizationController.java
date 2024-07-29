package com.senbao.controller;

import com.senbao.pojo.User;
import com.senbao.service.UserService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody User user){
        System.out.println("user = " + user);
        Result result = userService.login(user);
        return result;
    }

    @PostMapping("register")
    public Result register(@RequestBody User user){
        System.out.println("user = " + user);
        Result result = userService.register(user);
        return result;
    }




}
