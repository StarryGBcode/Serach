package com.cn.dao;

import com.cn.pojo.Surveys;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveysReposity extends JpaRepository<Surveys,Integer> {
//    Surveys findById(Integer id);
}
