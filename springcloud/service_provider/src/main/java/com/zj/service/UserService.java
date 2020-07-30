package com.zj.service;

import com.zj.dao.UserDao;
import com.zj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getUserById(Integer id){
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }
}
