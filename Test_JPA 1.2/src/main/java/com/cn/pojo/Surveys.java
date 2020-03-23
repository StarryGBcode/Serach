package com.cn.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "surveys")
public class Surveys implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "surveys_id")
    private Integer id;
    @Column(name = "qes_head")
    private String qes_head;
    @Column(name = "add_time")
    private Date add_time;
    @Column(name = "total")
    private Integer total;
    @Column(name = "type")
    private String type;
    @Column(name = "survey_desc")
    private String survey_desc;
    @Column(name = "survey_state")
    private Integer state;
    @Column(name = "ispublic")
    private boolean ispublic;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private Users users;
    @OneToMany(cascade = {CascadeType.REMOVE},mappedBy = "surveys",fetch = FetchType.EAGER)
    private Set<Questions> questionsSet = new HashSet<>();

    @Override
    public String toString() {
        return "Surveys{" +
                "id=" + id +
                ", qes_head='" + qes_head + '\'' +
                ", total=" + total +
                ", type='" + type + '\'' +
                ", survey_desc='" + survey_desc + '\'' +
                ", state=" + state +
                ", ispublic=" + ispublic +
                ", users=" + users +
                '}';
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSurvey_desc() {
        return survey_desc;
    }

    public void setSurvey_desc(String survey_desc) {
        this.survey_desc = survey_desc;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIspublic() {
        return ispublic;
    }

    public void setIspublic(boolean ispublic) {
        this.ispublic = ispublic;
    }


    public Integer getId() {
        return id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Set<Questions> getQuestionsSet() {
        return questionsSet;
    }

    public void setQuestionsSet(Set<Questions> questionsSet) {
        this.questionsSet = questionsSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQes_head() {
        return qes_head;
    }

    public void setQes_head(String qes_head) {
        this.qes_head = qes_head;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

}
