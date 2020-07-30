package com.zj.controller;

import com.zj.dao.DepartmentDao;
import com.zj.dao.EmployeeDao;
import com.zj.domain.Department;
import com.zj.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;
    @PostMapping("/user/login")
    public String login(String username, String password, HttpSession session, Map<String, Object> map){
        System.out.println("进入controller");
        if (username.equals("admin")&&password.equals("1234")){
            session.setAttribute("loginuser",username);
            return "redirect:/main.html";
        }else{
            map.put("msg","用户名或密码错误，请重新输入");
            return "index";
        }

    }
    @GetMapping("/emps")
    public String getEmps(Model model){
        System.out.println("查询所有员工信息");
        Collection<Employee> all = employeeDao.getAll();
        model.addAttribute("emps",all);
        return "emp/list";
    }
    @GetMapping("/user/loginout")
    public String loginout(HttpSession session){
        session.invalidate();
        return "index";
    }
    @GetMapping("/emp/{id}")
    public String findEmpById(@PathVariable Integer id,Model model){
        Employee employeeById = employeeDao.getEmployeeById(id);
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("emp",employeeById);
        model.addAttribute("departments",departments);
        return "emp/update";
    }
    @GetMapping("/delemp/{id}")
    public String deleteEmpById(@PathVariable Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }

    @GetMapping("/emp")
    public String forwardAdd(Model model){
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "/emp/add";
    }
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }
    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }
}
