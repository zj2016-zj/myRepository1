package com.zj.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zj.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getName() {
        return "hello,dubbox";
    }
}
