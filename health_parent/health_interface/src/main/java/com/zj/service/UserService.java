package com.zj.service;

import com.zj.pojo.User;

public interface UserService {
    User findUserByUsername(String username);
}
