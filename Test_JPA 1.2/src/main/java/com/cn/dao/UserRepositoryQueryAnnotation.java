package com.cn.dao;

import com.cn.pojo.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepositoryQueryAnnotation extends Repository<Users,Integer> {
    /*
     * 通过QueryAnnotation来查询
     * */
    @Query(value = "select * from t_users where name = ?",nativeQuery = true)
    List<Users> queryAllByNameSQL(String name);}
