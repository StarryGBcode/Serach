package com.cn.pojo;

import com.cn.App;
import com.cn.dao.RolesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = App.class)
class RolesTest_ManyToMany {

    @Autowired
    private RolesRepository rolesRepository;
    @Test
    public void testSave(){

        Roles role = new Roles();
        role.setRolename("项目经理");

        Menus menu = new Menus();
        menu.setMenusname("xxxx商城管理系统");
        menu.setFatherid(1);
        Menus menu2 = new Menus();
        menu2.setMenusname("项目管理");
        menu2.setFatherid(2);
        //关联
        role.getMenus().add(menu);
        role.getMenus().add(menu2);
        menu.getRoles().add(role);
        menu2.getRoles().add(role);
        //保存
        this.rolesRepository.save(role);
    }
    @Test
    public void testFind(){
        Roles roles = new Roles();
        roles.setRoleid(2);
        Example<Roles> rolesExample = Example.of(roles);
        Optional<Roles> r = this.rolesRepository.findOne(rolesExample);
        if (r.isPresent()){
            roles=r.get();
        }else roles=null;
        System.out.println(roles.getMenus());
    }
}