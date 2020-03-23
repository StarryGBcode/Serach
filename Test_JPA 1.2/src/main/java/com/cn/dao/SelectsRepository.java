package com.cn.dao;

import com.cn.pojo.Questions;
import com.cn.pojo.Selects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectsRepository extends JpaRepository<Selects,Integer> {
    List<Selects> findSelectsByQuestions(Questions questions);

}
