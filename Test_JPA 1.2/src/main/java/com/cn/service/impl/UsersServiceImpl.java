package com.cn.service.impl;

import com.cn.dao.UsersRepository;
import com.cn.pojo.Users;
import com.cn.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    @Cacheable(value = "users")
    public List<Users> findUsersAll() {
        return this.usersRepository.findAll();
    }

    @Override
    @Cacheable(value = "users")
    public Users findUserById(Integer id) {
        Users users=new Users();
        users.setId(id);
        Example<Users> exm = Example.of(users);
        Optional<Users> user = this.usersRepository.findOne(exm);
        if (user.isPresent()){
            users=user.get();
        }else
            users=null;
        return users;
    }

    @Override
    @Cacheable(value = "users",key = "#pageable.pageSize")
    public Page<Users> findUserByPage(Pageable pageable) {
        return this.usersRepository.findAll(pageable);
    }

    @Override
    @CacheEvict(value = "users" ,allEntries = true)
    public void saveUsers(Users users) {
        this.usersRepository.save(users);
    }
}
