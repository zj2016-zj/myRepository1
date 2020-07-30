package com.zj.controller;

import com.zj.pojo.User;
import com.zj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/provider/user/{id}")
    public User getUserById(@PathVariable("id") Integer id){
        System.out.println("进入controller");
        return userService.getUserById(id);
    }
}
