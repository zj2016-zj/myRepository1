package com.zj.service;

import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    Setmeal findById(Integer id);

    List<Integer> findCheckgroupIdsBysetmealId(Integer id);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    List<Setmeal> getAllSetmeal();

    Setmeal findDetailsById(Integer id);

    List<Map<String, Object>> findSetmealCount();

}
