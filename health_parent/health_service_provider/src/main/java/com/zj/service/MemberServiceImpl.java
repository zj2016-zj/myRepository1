package com.zj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zj.dao.MemberDao;
import com.zj.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;
    //根据电话号码查询会员
    @Override
    public Member findMemberByTel(String telephone) {
        Member memberByTelephone = memberDao.findMemberByTelephone(telephone);

        System.out.println("service中member:"+memberByTelephone);
        return memberByTelephone;
    }
    //当用户不是会员时，自动自己注册成会员
    @Override
    public void add(Member member) {
        System.out.println("进入addService中————前");
        memberDao.addMember(member);
        System.out.println("进入addService中————后");
    }
   //根据月份查找会员数量
    @Override
    public List<Integer> findMemberCountByMonth(ArrayList<String> month) {
        ArrayList<Integer> counts = new ArrayList<>();
        for (String m : month) {
            m=m+"-31";
            Integer count= memberDao.findMemberCountByMonth(m);
            counts.add(count);
            System.out.println("拼接后的月份"+m+"对应会员数"+count);
        }

        return counts;
    }
}
