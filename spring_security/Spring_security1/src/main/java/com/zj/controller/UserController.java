package com.zj.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/login.do")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        return "add";
    }
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('delete')")
    public String delete(){
        return "delete";
    }
}
