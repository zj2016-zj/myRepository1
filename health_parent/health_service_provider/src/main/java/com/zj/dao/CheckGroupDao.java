package com.zj.dao;

import com.github.pagehelper.Page;
import com.zj.pojo.CheckGroup;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.List;

public interface CheckGroupDao {
    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(Integer id);

    void addGroup(CheckGroup checkGroup);

    void addRelation(HashMap<String, Integer> map);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void clearRelation(Integer id);

    void editGroup(CheckGroup checkGroup);

    void deleteGroupById(Integer id);

    List<CheckGroup> findAll();

    List<CheckGroup> findCheckGroupBySetmealId(Integer id);
}
