package com.zj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;
    @RequestMapping("/showName.do")
    @ResponseBody
    public String showName(){
        String name = userService.getName();
        System.out.println("名字:"+name);
        return name;
    }
}
