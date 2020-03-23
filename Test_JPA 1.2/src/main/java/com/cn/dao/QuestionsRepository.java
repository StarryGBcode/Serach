package com.cn.dao;

import com.cn.pojo.Questions;
import com.cn.pojo.Surveys;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions,Integer> {
    //@Query("select q from Questions q where q.surveys=?1")
    List<Questions> findQuestionsBySurveys(Surveys surveys, Sort sort);
}
