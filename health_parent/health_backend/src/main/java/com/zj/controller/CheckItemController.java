package com.zj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zj.service.CheckItemService;
import com.zj.constant.MessageConstant;
import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.entity.Result;
import com.zj.pojo.CheckItem;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    CheckItemService checkItemService;

    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckItem checkItem ){
        try {
            checkItemService.addCheckItem(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page = checkItemService.findPage(queryPageBean);
        return  page ;
    }

    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        CheckItem checkItemById=null;
        try {
            checkItemById= checkItemService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }

        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS,checkItemById);
    }
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    @RequestMapping("/edit.do")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete.do")
    public Result delete(Integer id){
        try {
            checkItemService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    @RequestMapping("/findAll.do")
    public Result findAll(){
        List<CheckItem> checkItemList=null;
        try {
            checkItemList=checkItemService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
    }
}
