package com.zj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.zj.constant.MessageConstant;
import com.zj.constant.RedisMessageConstant;
import com.zj.entity.Result;
import com.zj.pojo.Member;
import com.zj.service.MemberService;
import jdk.nashorn.internal.objects.NativeDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RequestMapping("/member")
@RestController
public class MemberController {
    @Reference
    MemberService memberService;
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/login.do")
    public Result login(@RequestBody Map map, HttpServletResponse response){
        String telephone = (String) map.get("telephone");
        System.out.println("电话号码是："+telephone);

        String validateCode = (String) map.get("validateCode");
        System.out.println("前端中的code"+validateCode);

        String codeInRedis = jedisPool.getResource().get(telephone+RedisMessageConstant.SENDTYPE_LOGIN);
        System.out.println("redis中的code:"+codeInRedis);

        if (codeInRedis==null||!validateCode.equals(codeInRedis)){
            //验证码输入错误
            System.out.println("验证码输入错误");
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);

        }else {
            //当验证码输入正确就根据电话号码判断是否存在该会员，没有该会员自己注册成为会员
            Member member=memberService.findMemberByTel(telephone);

            System.out.println("会员查询是否为空：--");

            if (member==null){
                member=new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());

                System.out.println("添加数据后的member:"+member);

                memberService.add(member);
                System.out.println("controller中会员添加成功");
            }
            //写入cookie 跟踪用户 将数据保存到cookie中
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");//设置cookie的有效路径
            cookie.setMaxAge(60*60*24*30);//有效期30天
            response.addCookie(cookie);//发送cookie

            //保存会员信息到redis中
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone,60*30,json);
        }

        return new Result(true,MessageConstant.LOGIN_SUCCESS);

    }
}
