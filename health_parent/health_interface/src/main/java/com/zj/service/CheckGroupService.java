package com.zj.service;

import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    void addGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteGroup(Integer id);

    List<CheckGroup> findAll();

}

