package com.zj.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.zj.pojo.Permission;
import com.zj.pojo.Role;
import com.zj.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("进入到sercurity登陆中");
        User user=userService.findUserByUsername(username);
        if (user==null){
            return null;
        }
        List<GrantedAuthority> list=new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
             list.add(new SimpleGrantedAuthority(role.getKeyword()));
             Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);
        return userDetails;
    }
}
