package com.zj.dao;


import com.zj.domain.Department;
import com.zj.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//员工dao
@Repository
public class EmployeeDao {
    //    模拟数据
    private static Map<Integer, Employee> employees = null;
    @Autowired
    private DepartmentDao departmentDao;

    static {
        employees = new HashMap<Integer, Employee>(); //创建一个功能数据表
        employees.put(1001, new Employee(1001, "AA", "A126491@qq.com", 0, new Department(101, "教学部")));
        employees.put(1002, new Employee(1002, "BB", "B126491@qq.com", 1, new Department(102, "市场部")));
        employees.put(1003, new Employee(1003, "CC", "C126491@qq.com", 0, new Department(103, "教研部")));
        employees.put(1004, new Employee(1004, "DD", "D126491@qq.com", 1, new Department(104, "运营部")));
        employees.put(1005, new Employee(1005, "EE", "E126491@qq.com", 0, new Department(105, "后勤部")));
    }

    private static Integer initId = 1006;

    //    增加员工
    public void save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(initId++);
        }
        employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));
        employees.put(employee.getId(), employee);
    }

    //    查询全部员工信息
    public Collection<Employee> getAll() {
        return employees.values();
    }

    //    根据id查询员工信息
    public Employee getEmployeeById(Integer id) {
        return employees.get(id);
    }

    //    删除员工
    public void delete(Integer id) {
        employees.remove(id);
    }
}
