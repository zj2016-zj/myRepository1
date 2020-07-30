package com.zj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zj.constant.MessageConstant;
import com.zj.constant.RedisConstant;
import com.zj.entity.PageResult;
import com.zj.entity.QueryPageBean;
import com.zj.entity.Result;
import com.zj.pojo.Setmeal;
import com.zj.service.SetmealService;
import com.zj.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    SetmealService setmealService;
    @Autowired
    JedisPool jedisPool;
    //文件上传回显操作处理
    @RequestMapping("/upload.do")
   public Result upload(MultipartFile imgFile){//会自动将上传的文件封装成对象
        String originalFilename = imgFile.getOriginalFilename();
        System.out.println("獲取到原始文件名稱："+originalFilename);//获取到文件名
        int index = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index - 1);
        String newFileName=UUID.randomUUID().toString()+extention;
        try {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
            //利用七牛云上傳工具類进行图片上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,newFileName);
   }
   @RequestMapping("/add.do")
   //套餐添加
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds ){
        try {
            setmealService.addSetmeal(setmeal,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
         PageResult pageResult=setmealService.findPage(queryPageBean);
        return pageResult;
    }
    @RequestMapping("/delete.do")
    public Result delete(Integer id){
        try {
            setmealService.delete(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        try {
            Setmeal setmeal=setmealService.findById(id);
            return  new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/findCheckgroupIdsBysetmealId.do")
    public Result findCheckgroupIdsBysetmealId(Integer id){
        try {
            List<Integer> ids=setmealService.findCheckgroupIdsBysetmealId(id);
            return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,ids);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.edit(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }
}
