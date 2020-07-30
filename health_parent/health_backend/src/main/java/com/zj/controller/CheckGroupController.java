package com.zj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zj.service.CheckGroupService;
import com.zj.constant.MessageConstant;
import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.entity.Result;
import com.zj.pojo.CheckGroup;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/checkgroup")
@RestController
public class CheckGroupController {

    @Reference
    CheckGroupService checkGroupService;

    //进行分页查询操作
    @RequestMapping("/findPage")
    public PageResult  findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pages=checkGroupService.findPage(queryPageBean);
        return pages;
    }
    //添加检查组信息
    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        try {
            checkGroupService.addGroup(checkGroup,checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(true, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return  new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    //进行回显操作，根据id查询检查组的信息
    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        CheckGroup checkGroup=null;
        try {
            checkGroup=checkGroupService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroup);
    }
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        List<Integer> checkItemIds=null;
        try {
            checkItemIds=checkGroupService.findCheckItemIdsByCheckGroupId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);

        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return  new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @RequestMapping("/delete.do")
    public Result deleteGroup(Integer id){
        try {
            checkGroupService.deleteGroup(id);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return  new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
    @RequestMapping("/findAll.do")
    public Result findAll(){
        try {
            List<CheckGroup> checkGroupList=checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
