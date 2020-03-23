package com.cn.dao;

import com.cn.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Integer> {
//    Users findByUser_name(String name);
    Users findByUsername(String name);
}
