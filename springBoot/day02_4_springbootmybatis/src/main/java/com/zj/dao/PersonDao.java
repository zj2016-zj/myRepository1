package com.zj.dao;

import com.zj.domain.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PersonDao {
    @Select("select * from person")
    List<Person> findAllPerson();
}
