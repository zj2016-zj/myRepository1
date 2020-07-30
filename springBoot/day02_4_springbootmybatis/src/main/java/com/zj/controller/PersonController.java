package com.zj.controller;

import com.zj.dao.PersonDao;
import com.zj.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonDao personDao;
    @GetMapping("/findPerson")
    public String getPerson(){
        List<Person> allPerson = personDao.findAllPerson();
        String s = allPerson.get(0).toString();
        System.out.println(s);
        return s;
    }
}
