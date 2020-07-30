package com.zj.dao;

import com.zj.pojo.User;

public interface UserDao {
    User findUserByUsername(String username);
}
