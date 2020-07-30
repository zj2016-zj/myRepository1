package com.zj.dao;

import com.zj.pojo.OrderSetting;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface OrderSettingDao {

    long getCountByDate(Date orderDate);

    void updateOrderSetting(OrderSetting orderSetting);

    void addOrderSetting(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(HashMap<String,String> map);

    OrderSetting findByOrderDate(Date date);

    void upateOrderSetting(OrderSetting orderSetting);
}
