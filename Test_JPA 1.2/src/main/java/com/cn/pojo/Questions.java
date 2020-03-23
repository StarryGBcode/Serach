package com.cn.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Questions")
public class Questions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ques_id")
    private Integer id;
    @Column(name = "ques_tex")
    private String ques_tex;
    @Column(name = "ques_type")
    private Integer ques_type;
    @ManyToOne
    @JoinColumn(name = "surveys_id")
    private Surveys surveys;
    @OneToMany(cascade = {CascadeType.REMOVE},mappedBy = "questions",fetch = FetchType.EAGER)
    private Set<Selects> selectsSet;

    public Set<Selects> getSelectsSet() {
        return selectsSet;
    }

    public void setSelectsSet(Set<Selects> selectsSet) {
        this.selectsSet = selectsSet;
    }

    public Integer getQues_type() {
        return ques_type;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "id=" + id +
                ", ques_tex='" + ques_tex + '\'' +
                ", ques_type=" + ques_type +
                ", selectsSet=" + selectsSet +
                '}';
    }

    public void setQues_type(Integer ques_type) {
        this.ques_type = ques_type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQues_tex() {
        return ques_tex;
    }

    public void setQues_tex(String ques_tex) {
        this.ques_tex = ques_tex;
    }

    public Surveys getSurveys() {
        return surveys;
    }

    public void setSurveys(Surveys surveys) {
        this.surveys = surveys;
    }

}
