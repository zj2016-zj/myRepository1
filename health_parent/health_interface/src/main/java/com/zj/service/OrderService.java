package com.zj.service;

import com.zj.entity.Result;

import java.util.Map;

public interface OrderService {
    Result addOrder(Map map);

    Map findByOrderId(Integer id);
}
