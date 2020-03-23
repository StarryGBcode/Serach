package com.cn.pojo;

import lombok.Data;

@Data
public class MyUsers {
    private long id;
    private String name;
    private String password;
    private String tel;
    private Integer sex;
    private Integer age;
}
