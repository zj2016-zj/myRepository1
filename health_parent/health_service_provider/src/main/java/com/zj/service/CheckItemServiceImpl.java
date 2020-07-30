package com.zj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zj.dao.CheckItemDao;
import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    CheckItemDao checkItemDao;
 //添加检查项的信息
    @Override
    public void addCheckItem(CheckItem checkItem) {
        checkItemDao.addCheckItem(checkItem);
    }
//查询信息进行分页展示数据
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> checkItems=checkItemDao.findPage(queryString);


        long total = checkItems.getTotal();
        return new PageResult(total,checkItems);
    }
//根据检查项id查询检查项数据，进行回显操作
    @Override
    public CheckItem findById(Integer cid) {
        CheckItem CheckItemById = checkItemDao.findById(cid);
        return CheckItemById;
    }
//进行数据的修改操作
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }
//进行检查项的删除操作
    @Override
    public void deleteById(Integer id) {
         int count=checkItemDao.findCountById(id);
         if (count>0){
             throw new RuntimeException();
         }else{
             checkItemDao.deleteById(id);
         }
    }
//查找全部检查项信息
    @Override
    public List<CheckItem> findAll() {
        return  checkItemDao.findAll();

    }
}
