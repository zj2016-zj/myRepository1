package com.zj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zj.constant.RedisConstant;
import com.zj.dao.SetmealDao;
import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;
    @Autowired
    JedisPool jedisPool;
    //进行套餐添加操作
    @Override
    public void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.addSetmeal(setmeal);
        Integer id = setmeal.getId();
        this.addRelation(id,checkgroupIds);
        String img = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);
    }
//分页操作
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> setmealPages=setmealDao.findPage(queryString);
        return new PageResult(setmealPages.getTotal(),setmealPages);
    }

    @Override
    public void delete(Integer id) {
        //先删除中间表的关系
       setmealDao.deleteRelation(id);
        //再删除套餐表中的数据
        setmealDao.deleteSetmea(id);
    }
   //编辑操作，用来进行套餐的回显操作
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }
    //根据套餐查询检查组的id用来进行回显操作
    @Override
    public List<Integer> findCheckgroupIdsBysetmealId(Integer id) {
        return setmealDao.findCheckgroupIdsBysetmealId(id);
    }
    //进行套餐表的编辑
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //删除两表的关联
        setmealDao.deleteRelation(setmeal.getId());
        //添加套餐项
        setmealDao.edit(setmeal);
        //添加两表的关系
        this.addRelation(setmeal.getId(),checkgroupIds);
    }
   //查找所有套餐信息
    @Override
    public List<Setmeal> getAllSetmeal() {

        return  setmealDao.getAllSetmeal();
    }
//根据套餐id查询检查组的所有详细信息
    @Override
    public Setmeal findDetailsById(Integer id) {
        return setmealDao.findDetailsById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    //添加检查组和套餐之间的关系
    public void addRelation(Integer setmeal_id,Integer[] checkgroupIds){
       if (checkgroupIds != null && checkgroupIds.length > 0){
           for (Integer checkgroup_id : checkgroupIds) {
               HashMap<String, Integer> map = new HashMap<>();
               map.put("setmeal_id",setmeal_id);
               map.put("checkgroup_id",checkgroup_id);
               setmealDao.addRelation(map);
           }
       }
    }
}
