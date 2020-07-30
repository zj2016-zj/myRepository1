package com.zj.service;

import com.zj.domain.PageBean;

public interface RouteService {
    PageBean findPage(Integer cid,Integer currentPage,Integer pageSize,String word);
}
