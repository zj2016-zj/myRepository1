package com.zj.dao;

import com.github.pagehelper.Page;
import com.zj.domain.Route;

import java.util.List;

public interface RouteDao {
    Page<Route> findPage(String word);
}
