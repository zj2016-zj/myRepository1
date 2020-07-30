package com.zj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.zj.constant.MessageConstant;
import com.zj.constant.RedisMessageConstant;
import com.zj.entity.Result;
import com.zj.pojo.Order;
import com.zj.service.OrderService;
import com.zj.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    JedisPool jedisPool;

    @Reference
    OrderService orderService;
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        //获取手机号码，
        String telephone = (String) map.get("telephone");
        //从缓存中获取数据
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //获取从前端传过来的数据
        String validateCode =(String)map.get("validateCode");
        //判断验证码输入是否正确
        if (codeInRedis==null||!codeInRedis.equals(validateCode)){
            //验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result=null;
        //可以从前端获取的数据有name sex telephone validateCode idCard orderDate setmealId sex
        //需要进行添加的数据有id member_id orderDate orderType  orderStatus setmeal_id
        try {
            map.put("order_Type", Order.ORDERTYPE_WEIXIN);
            result=orderService.addOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result.isFlag()){
            //预约成功  发送短信通知
            String orderDate = (String) map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    @RequestMapping("/findById.do")
    public Result findByOrderId(Integer id){
        try {
            Map map=orderService.findByOrderId(id);
            //查询预约信息成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
