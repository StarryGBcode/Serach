package com.cn.pojo;

import com.cn.App;
import com.cn.dao.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = App.class)
public class RolesTest {


    @Autowired
    private UsersRepository usersRepository;
    @Test
    public void TestSave(){
        Users users=new Users();
        users.setAddress("合肥");
        users.setName("李宁");
        users.setAge(21);

        Roles roles=new Roles();
        roles.setRolename("管理员");

        roles.getUsers().add(users);
        users.setRoles(roles);
        this.usersRepository.save(users);
    }
    @Test
    public  void testFind(){
        Users users=new Users();
        users.setId(6);
        Example<Users> exm = Example.of(users);
        Optional<Users> user=this.usersRepository.findOne(exm);
//        if (optional.isPresent()){
//            ayUser=optional.get();
//            return  ayUser;
//        }else{
//            return  null;
//        }
        if (user.isPresent()){
            users=user.get();
        }else
            users=null;
        System.out.println(users);
        Roles roles= users.getRoles();
        System.out.println(roles.getRolename());
    }

}