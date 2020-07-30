package com.zj.controller;

import com.zj.domain.PageBean;
import com.zj.domain.Route;
import com.zj.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
public class RouteController {
   @Autowired
    RouteService routeService;
    @RequestMapping("/findPage.do")
    public PageBean findPage(Integer cid,Integer currentPage,Integer pageSize,String word){
        System.out.println("进入RouteController____,cid:"+cid+";currentPage:"+currentPage+";pageSize:"+pageSize+";word:"+word);
        PageBean page = routeService.findPage(cid, currentPage, pageSize, word);
        System.out.println("完成分页查询操作");
        return page;
    }
}
