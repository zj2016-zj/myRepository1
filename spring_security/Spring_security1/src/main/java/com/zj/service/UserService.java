package com.zj.service;

import com.zj.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public class UserService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    Map<String,UserInfo> map=new HashMap<>();

    public void init(){
        UserInfo u1 = new UserInfo();
        u1.setUsername("admin");
        u1.setPassword(passwordEncoder.encode("admin"));
        UserInfo u2 = new UserInfo();
        u2.setUsername("user");
        u2.setPassword(passwordEncoder.encode("user"));
        map.put(u1.getUsername(),u1);
        map.put(u2.getUsername(),u2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username:"+username);
        init();
        UserInfo userInfo = map.get(username);
        System.out.println("userInfo"+userInfo);
        if (userInfo==null){
            return null;
        }
        String password = userInfo.getPassword();
        List<GrantedAuthority> list=new ArrayList<>();
        if (username.equals("admin")){
            list.add(new SimpleGrantedAuthority("add"));
            list.add(new SimpleGrantedAuthority("delete"));
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        User user = new User(username,password,list);
        return user;
    }
}
