package com.cn.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Selects")
public class Selects implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sel_id;
    private String sel_text;
    private Integer count_total;
    @ManyToOne
    @JoinColumn(name = "questions_id")
    private Questions questions;

    @Override
    public String toString() {
        return "Selects{" +
                "sel_id=" + sel_id +
                ", sel_text='" + sel_text + '\'' +
                ", count_total=" + count_total +
                '}';
    }

    public Integer getCount_total() {
        return count_total;
    }

    public void setCount_total(Integer count_total) {
        this.count_total = count_total;
    }

    public Integer getSel_id() {
        return sel_id;
    }

    public void setSel_id(Integer sel_id) {
        this.sel_id = sel_id;
    }

    public String getSel_text() {
        return sel_text;
    }

    public void setSel_text(String sel_text) {
        this.sel_text = sel_text;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

}
