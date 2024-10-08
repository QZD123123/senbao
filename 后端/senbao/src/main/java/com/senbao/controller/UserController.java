package com.senbao.controller;

import com.senbao.pojo.User;
import com.senbao.service.UserService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("")
    public Result userPageSelect(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String role){
        Result result =userService.userPageSelect(page,pageSize,role);
        return result;
    }

    @GetMapping("{id}")
    public Result selectUserById(@PathVariable Integer id){
        Result result = userService.selectUserById(id);
        return result;
    }

    @PatchMapping("{id}")
    public Result updateUserById(@PathVariable Integer id, @RequestBody User user){
        System.out.println("user = " + user);
        System.out.println("id = " + id);
        Result result = userService.updateUserById(id,user);
        return result;
    }

    @DeleteMapping("{id}")
    public Result deleteUser(@PathVariable Integer id){
        Result result = userService.deleteUser(id);
        return result;
    }

}
