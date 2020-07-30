package com.zj.dao;

import com.zj.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    Set<Permission> findPermissionByRoleId(Integer roleId);
}
