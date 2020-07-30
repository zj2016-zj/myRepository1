package com.zj.dao;

import com.zj.pojo.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    void addOrder(Order order);

    Map findByOrderId(Integer id);

    List<Order> findByCondition(Order order);

    Integer getTodayOrderNumber(String today);

    Integer getTodayVisitsNumber(String today);

    Integer getThisWeekOrMonthOrderNumber(String weekFirst);

    Integer thisWeekOrMonthVisitsNumber(String weekFirst);

    List<Map<String, Object>> getHotSetmeal();

}
