package com.zj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zj.constant.MessageConstant;
import com.zj.entity.Result;
import com.zj.service.MemberService;
import com.zj.service.ReportService;
import com.zj.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    MemberService memberService;
    @Reference
    SetmealService setmealService;
    @Reference
    ReportService reportService;
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        System.out.println("进入到折线图controller中");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        ArrayList<String> month = new ArrayList<>();
        for (int i = 0;i < 12;i++) {
            calendar.add(Calendar.MONTH,1);
            month.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("months",month);
        List<Integer> memberCount=memberService.findMemberCountByMonth(month);
        //--------------------------------------------------------------------
        for (Integer integer : memberCount) {
            System.out.println("输出每个月的数量"+integer);
        }
        //-------------------------------------------------------------------
        map.put("memberCount",memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }
    @RequestMapping("/getSetmealReport")
    public  Result getSetmealReport(){
        List<Map<String,Object>> steamlCount=setmealService.findSetmealCount();

        HashMap<String, Object> setmealMessage = new HashMap <>();

        ArrayList<String> setmealNames = new ArrayList<>();
        for (Map<String, Object> map : steamlCount) {
            String name = (String) map.get("name");
            setmealNames.add(name);
        }
        setmealMessage.put("setmealCount",steamlCount);
        setmealMessage.put("setmealNames",setmealNames);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,setmealMessage);
    }
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){

        try {
            Map<String,Object> businessMessageMap=reportService.getBusinessMessage();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,businessMessageMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }
    /**reportDate
     * todayNewMember :0,totalMember :0,thisWeekNewMember :0,thisMonthNewMember :0,
     * todayOrderNumber :0,todayVisitsNumber :0,thisWeekOrderNumber :0,thisWeekVisitsNumber :0,
     * thisMonthOrderNumber :0,thisMonthVisitsNumber :0,hotSetmeal
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            Map<String,Object> result = reportService.getBusinessMessage();

            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            String filePath = request.getSession().getServletContext().getRealPath("template")+ File.separator+"report_template.xlsx";

            System.out.println("filePath:"+
                    filePath
            );
            //基于提供的Excel模板文件在内存中创建一个Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            //读取第一个工作表
            XSSFSheet sheet = excel.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for(Map map : hotSetmeal){//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            //使用输出流进行表格下载,基于浏览器作为客户端下载
            OutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");//代表的是Excel文件类型
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");//指定以附件形式进行下载
            excel.write(out);

            out.flush();
            out.close();
            excel.close();
            return null;
        }catch (Exception e){
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }


}
