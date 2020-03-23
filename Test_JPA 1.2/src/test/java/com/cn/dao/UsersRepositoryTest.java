package com.cn.dao;

import com.cn.App;
import com.cn.pojo.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.DigestUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {App.class})
class UsersRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;
    @Test
    public void testSave(){
        Users users =new Users();
        //users.setId(15);
        users.setName("李小四mdttt");
        users.setAge(32);
        users.setAddress("南昌");
        users.setPassword(DigestUtils.md5DigestAsHex("lisi123".getBytes()));
        users.setAdd_time(new Date());
        users.setUsername("lisi123");
        try {
            Users  u = this.usersRepository.save(users);
            System.out.println(u);
        }catch (ConstraintViolationException e){
            e.printStackTrace();
        }

//        System.out.println(DigestUtils.md5DigestAsHex(users.getPassword().getBytes()));
        //System.out.println(this.usersRepository.findByUsername("lisi1233"));;
    }
    @Test
    public void testJPARepository(){
        Pageable pageable = PageRequest.of(10,5,Sort.by(Sort.Direction.DESC,"id"));
        Page<Users> users= this.usersRepository.findAll(pageable);
        System.out.println(users.getContent());
    }

}