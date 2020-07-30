package com.zj.dao;

import com.github.pagehelper.Page;
import com.zj.pojo.Setmeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SetmealDao {

    void addSetmeal(Setmeal setmeal);

    void addRelation(HashMap<String, Integer> map);

    Page<Setmeal> findPage(String queryString);

    void deleteRelation(Integer id);

    void deleteSetmea(Integer id);

    Setmeal findById(Integer id);

    List<Integer> findCheckgroupIdsBysetmealId(Integer id);

    void edit(Setmeal setmeal);

    List<Setmeal> getAllSetmeal();

    Setmeal findDetailsById(Integer id);

    List<Map<String, Object>> findSetmealCount();

}
