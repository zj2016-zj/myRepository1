package com.zj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zj.constant.MessageConstant;
import com.zj.entity.Result;
import com.zj.pojo.OrderSetting;
import com.zj.service.OrderSettingService;
import com.zj.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            //1、取得文件中数据，每一行数据存储到一个string数组中
            List<String[]> strings = POIUtils.readExcel(excelFile);
            //2、为了将数据能够保存到数据库中，将string数组转为ordersetting对象进行存储
            List<OrderSetting> orderSettingList=new ArrayList<>();
            //3、遍历从文件中获取的数据，根据下标获取数组中的值
            for (String[] string : strings) {
                String orderDate = string[0];
                String number = string[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate),new Integer(number));
                orderSettingList.add(orderSetting);
            }
            //将该list集合传入到service中。进行业务处理
            orderSettingService.add(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        System.out.println("进入controller");
        try {
            List<Map> list=orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
