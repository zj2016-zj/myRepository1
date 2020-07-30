package com.zj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zj.dao.PermissionDao;
import com.zj.dao.RoleDao;
import com.zj.dao.UserDao;
import com.zj.pojo.Permission;
import com.zj.pojo.Role;
import com.zj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PermissionDao permissionDao;
    @Override
    public User findUserByUsername(String username) {
        User user=userDao.findUserByUsername(username);
        if (user==null){
            return null;
        }
        Integer userId = user.getId();
        Set<Role> roles=roleDao.findRoleByUserId(userId);
        if (roles!=null || roles.size()>0){
            for (Role role : roles) {
                Integer roleId = role.getId();
                Set<Permission> permissions=permissionDao.findPermissionByRoleId(roleId);
                if(permissions!=null||permissions.size()>0){
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }
}
