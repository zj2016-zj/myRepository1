package com.zj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zj.dao.MemberDao;
import com.zj.dao.OrderDao;
import com.zj.dao.ReportDao;
import com.zj.dao.SetmealDao;
import com.zj.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportDao reportDao;
    @Autowired
    MemberDao memberDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    SetmealDao setmealDao;
    @Override
    public Map<String, Object> getBusinessMessage() throws Exception {
        /**reportDate
         * todayNewMember :0,totalMember :0,thisWeekNewMember :0,thisMonthNewMember :0,
         * todayOrderNumber :0,todayVisitsNumber :0,thisWeekOrderNumber :0,thisWeekVisitsNumber :0,
         * thisMonthOrderNumber :0,thisMonthVisitsNumber :0,hotSetmeal
         */
        HashMap<String, Object> businessMessage = new HashMap<>();
        Date today = DateUtils.getToday();

        String todayTime = DateUtils.parseDate2String(today);//当天
        String weekFirst = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());//本周一时间
        String monthFirst = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        businessMessage.put("reportDate",todayTime);
        Integer todayNewMember=memberDao.gettodayNewMember(todayTime);//
        businessMessage.put("todayNewMember",todayNewMember);//保存今日新增会员数
        Integer totalMember= memberDao.getTotalMember();
        businessMessage.put("totalMember",totalMember);//保存总的会员数
        Integer thisWeekNewMember= memberDao.getThisWeekOrMonthNewMember(weekFirst);
        businessMessage.put("thisWeekNewMember",thisWeekNewMember);//保存这周新增会员数
        Integer thisMonthNewMember = memberDao.getThisWeekOrMonthNewMember(monthFirst);
        businessMessage.put("thisMonthNewMember",thisMonthNewMember);//保存当月新增会员数
        Integer todayOrderNumber=orderDao.getTodayOrderNumber(todayTime);
        businessMessage.put("todayOrderNumber",todayOrderNumber);//保存当天预定人数
        Integer todayVisitsNumber=orderDao.getTodayVisitsNumber(todayTime);
        businessMessage.put("todayVisitsNumber",todayVisitsNumber);//保存当天已到诊人数
        Integer thisWeekOrderNumber= orderDao.getThisWeekOrMonthOrderNumber(weekFirst);
        businessMessage.put("thisWeekOrderNumber",thisWeekOrderNumber);//保存本周预定人数
        Integer thisWeekVisitsNumber=orderDao.thisWeekOrMonthVisitsNumber(weekFirst);
        businessMessage.put("thisWeekVisitsNumber",thisWeekVisitsNumber);//保存本周到诊人数
        Integer thisMonthOrderNumber=orderDao.getThisWeekOrMonthOrderNumber(monthFirst);
        businessMessage.put("thisMonthOrderNumber",thisMonthOrderNumber);//本月预定人数
        Integer thisMonthVisitsNumber=orderDao.getThisWeekOrMonthOrderNumber(monthFirst);
        businessMessage.put("thisMonthVisitsNumber",thisMonthVisitsNumber);//保存本月到诊人数
        List<Map<String,Object>> hotSetmeal=orderDao.getHotSetmeal();
        businessMessage.put("hotSetmeal",hotSetmeal);
        return businessMessage;
    }
}
