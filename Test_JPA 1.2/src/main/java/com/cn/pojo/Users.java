package com.cn.pojo;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="t_users")
@Data
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    @Size(max = 16,min = 4)
    @NotNull
    private String username;
    @Column(name = "password")
    @Size(max = 32,min = 4)
    @NotNull
    private String password;
    @Column(name = "add_time")
    private Date add_time;
    @Column(name = "age")
    private Integer age;
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "users")
    private Set<Surveys> surveysSet = new HashSet<Surveys>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "roles_id")
    private Roles roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
