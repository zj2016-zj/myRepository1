package com.zj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zj.constant.MessageConstant;
import com.zj.dao.MemberDao;
import com.zj.dao.OrderDao;
import com.zj.dao.OrderSettingDao;
import com.zj.entity.Result;
import com.zj.pojo.Member;
import com.zj.pojo.Order;
import com.zj.pojo.OrderSetting;
import com.zj.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
     MemberDao memberDao;

    @Override
    public Result addOrder(Map map) {
        //可以从前端获取的数据有name sex telephone validateCode idCard orderDate setmealId sex
        //需要进行添加的数据有id member_id orderDate orderType  orderStatus setmeal_id
        //1、首先根据电话判断该用户是不是会员
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findMemberByTelephone(telephone);
        if (member==null){
                member = new Member();
                member.setName((String) map.get("name"));
                member.setPhoneNumber(telephone);
                member.setIdCard((String) map.get("idCard"));
                member.setSex((String) map.get("sex"));
                member.setRegTime(new Date());
                memberDao.addMember(member);
        }
        //2、再判断ordersSetting中是否存在该日期，且判断可预约人数是否大于已预约人数
        String orderDate = (String)map.get("orderDate");
        Date date = null;
        try {
            date = DateUtils.parseString2Date(orderDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取到预约信息
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        if(orderSetting==null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        int number = orderSetting.getNumber();//可预约人数
        int reservations = orderSetting.getReservations();//已预约人数
        if(reservations==number){
            return new Result(false,MessageConstant.ORDER_FULL);//预约已满，不能预约
        }
        //3、根据日期和套餐信息检查用户的预约是否重复
        Integer memberId = member.getId();//会员ID
        Integer setmealId = Integer.parseInt((String)map.get("setmealId"));
        Order order1 = new Order(memberId,date,null,null,setmealId);
        List<Order> list = orderDao.findByCondition(order1);
        if(list!=null&&list.size()>0){
            //该会员已经预约过，不能再次预约
            return new Result(false,MessageConstant.HAS_ORDERED);
        }
        //4、进行预约信息的更新，以及预定信息添加
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.upateOrderSetting(orderSetting);
        Order order = new Order(member.getId(),date,(String)map.get("orderType"),Order.ORDERSTATUS_NO,Integer.parseInt((String)map.get("setmealId")));
        orderDao.addOrder(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    @Override
    public Map findByOrderId(Integer id) {
        Map map=orderDao.findByOrderId(id);
        if(map!=null){
            Date orderDate = (Date)map.get("orderDate");
            try {
                map.put("orderDate",DateUtils.parseDate2String(orderDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
