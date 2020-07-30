package com.zj.controller;
import com.zj.client.UserClient;
import com.zj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserClient userClient;
    @GetMapping("/consume/user")
    public User getUserById(Integer id){
       return  userClient.getUserById(id);
    }
}
