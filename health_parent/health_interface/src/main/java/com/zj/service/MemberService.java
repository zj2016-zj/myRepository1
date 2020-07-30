package com.zj.service;

import com.zj.pojo.Member;

import java.util.ArrayList;
import java.util.List;

public interface MemberService {
    Member findMemberByTel(String telephone);

    void add(Member member);

    List<Integer> findMemberCountByMonth(ArrayList<String> month);
}
