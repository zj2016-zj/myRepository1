package com.zj.service;

import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
//    添加检查项
    void addCheckItem(CheckItem checkItem);
//    进行分页查询检查项操作
    PageResult findPage(QueryPageBean queryPageBean);
//    根据id查找检查项
    CheckItem findById(Integer cid);
//    编辑检查项
    void edit(CheckItem checkItem);
//根据id删除检查项
    void deleteById(Integer id);
//查找全部检查项信息
    List<CheckItem> findAll();
}
