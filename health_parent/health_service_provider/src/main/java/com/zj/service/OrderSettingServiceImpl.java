package com.zj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zj.dao.OrderSettingDao;
import com.zj.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList!=null){
            for (OrderSetting orderSetting : orderSettingList) {
                Date orderDate=orderSetting.getOrderDate();
                long count=orderSettingDao.getCountByDate(orderDate);
                if (count>0){
                    orderSettingDao.updateOrderSetting(orderSetting);
                }else{
                    orderSettingDao.addOrderSetting(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        System.out.println("进入service中-------------------------");
        String start=date+"-1";
        String end=date+"-31";

        System.out.println("start:"+start);
        System.out.println("end:"+end);

        HashMap<String, String> map = new HashMap<>();
        map.put("start",start);
        map.put("end",end);

        List<OrderSetting> orderSettingList=orderSettingDao.getOrderSettingByMonth(map);
        System.out.println("dao中orderSettingList的值有无：----"+orderSettingList);

        ArrayList<Map> list = new ArrayList<>();
            for (OrderSetting orderSetting : orderSettingList) {

                System.out.println("getOrderSettingByMonthDao中————"+orderSetting);

                HashMap<String, Object> maps = new HashMap<>();
                maps.put("date",orderSetting.getOrderDate().getDate());//获取日期数字（几号）
                maps.put("number",orderSetting.getNumber());
                maps.put("reservations",orderSetting.getReservations());
                list.add(maps);
            }
        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        //根据日期查询是否已经进行了预约设置
        long count = orderSettingDao.getCountByDate(orderDate);
        if(count > 0){
            //当前日期已经进行了预约设置，需要执行更新操作
            orderSettingDao.updateOrderSetting(orderSetting);
        }else{
            //当前日期没有就那些预约设置，需要执行插入操作
            orderSettingDao.addOrderSetting(orderSetting);
        }
    }
}
