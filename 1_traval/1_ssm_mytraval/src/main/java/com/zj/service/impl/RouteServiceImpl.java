package com.zj.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zj.dao.RouteDao;
import com.zj.domain.PageBean;
import com.zj.domain.Route;
import com.zj.service.RouteService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    RouteDao routeDao;
    @Override
    public PageBean findPage(Integer cid, Integer currentPage, Integer pageSize, String word) {

        PageHelper.startPage(currentPage,pageSize);

        Page<Route> page = routeDao.findPage(word);
        int total = (int)page.getTotal();
        PageBean<Route> pageBean = new PageBean<>();

        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(total);
        pageBean.setList(page);
        pageBean.setTotalPage((total/pageSize)+1);

        System.out.println("service中"+pageBean);
        return pageBean;
    }
}
