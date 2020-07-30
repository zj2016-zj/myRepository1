package com.zj.dao;

import com.zj.pojo.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findRoleByUserId(Integer userId);
}
