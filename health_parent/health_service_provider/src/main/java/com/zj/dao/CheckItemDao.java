package com.zj.dao;

import com.github.pagehelper.Page;
import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.pojo.CheckItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CheckItemDao {
    //添加检查项
    void addCheckItem(CheckItem checkItem);
//    查询分页所需检查项信息
    Page<CheckItem> findPage(String queryString);
//    根据id查找检查项，进行回显操作
    CheckItem findById(Integer cid);
    //对检查项进行编辑操作
    void edit(CheckItem checkItem);
    //根据id进行检查项的统计
    int findCountById(Integer id);

    void deleteById(Integer id);

    List<CheckItem> findAll();

    List<CheckItem> findCheckItemByCheckGroupId(Integer id);
}
