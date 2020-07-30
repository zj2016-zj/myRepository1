package com.zj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zj.dao.CheckGroupDao;
import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    CheckGroupDao checkGroupDao;
    //进行分页操作
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);

        Page<CheckGroup> checkGroups=checkGroupDao.findPage(queryString);
        long total = checkGroups.getTotal();
        return new PageResult(total,checkGroups);
    }
    //进行回显操作，根据id查询检查组的信息
    @Override
    public CheckGroup findById(Integer id) {
        return  checkGroupDao.findById(id);
    }
//添加检查组
    @Override
    public void addGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.addGroup(checkGroup);
        this.addRelation(checkGroup.getId(),checkitemIds);
    }
 //查询选中的检查项
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }
  //更新检查组数据
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        Integer id = checkGroup.getId();
        checkGroupDao.clearRelation(id);
        checkGroupDao.editGroup(checkGroup);
        this.addRelation(id,checkitemIds);
    }
//删除检查组
    @Override
    public void deleteGroup(Integer id) {
        //1、要先删去检查项和检查组的关系
        checkGroupDao.clearRelation(id);
        //2、再删除检查组中的数据
        checkGroupDao.deleteGroupById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return  checkGroupDao.findAll();
    }

    //向中间表添加检查组和检查项关系
    public void addRelation(int groupId, Integer[] checkitemIds){
        for (Integer checkitemId : checkitemIds) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("checkgroup_id",groupId);
            map.put("checkitem_id",checkitemId);
            checkGroupDao.addRelation(map);
        }
    }
}
