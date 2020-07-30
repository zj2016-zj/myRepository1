package com.zj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zj.constant.MessageConstant;
import com.zj.entity.Result;
import com.zj.pojo.Setmeal;
import com.zj.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    SetmealService setmealService;

    @RequestMapping("/getAllSetmeal.do")
    public Result getAllSetmeal(){
        try {
            System.out.println("getAllSetmeal-----controller-----");
            List<Setmeal> setmealList= setmealService.getAllSetmeal();
            System.out.println("setmealList"+setmealList);
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal=setmealService.findDetailsById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
