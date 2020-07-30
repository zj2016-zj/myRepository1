package com.zj.dao;

import com.zj.pojo.User;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Table;

@Table(name="user")
public interface UserDao extends Mapper<User>{
}
