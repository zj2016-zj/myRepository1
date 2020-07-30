package com.zj.dao;

import com.zj.pojo.Member;

import java.util.ArrayList;
import java.util.Date;

public interface MemberDao {
    Member findMemberByTelephone(String telephone);

    void addMember(Member member);

   Integer findMemberCountByMonth(String month);

    Integer gettodayNewMember(String today);

    Integer getTotalMember();

    Integer getThisWeekOrMonthNewMember(String thisWeekMonday);

}
