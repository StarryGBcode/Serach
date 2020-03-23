package com.cn.dao;

import com.alibaba.fastjson.JSON;
import com.cn.App;
import com.cn.pojo.Questions;
import com.cn.pojo.Selects;
import com.cn.pojo.Surveys;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = App.class)
class SurveysReposityTest {
    @Autowired
    private SurveysReposity surveysReposity;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private SelectsRepository selectsRepository;
    @Test
    public void testSaveSurvey(){
        Surveys surveys=new Surveys();
        surveys.setQes_head("这是个测试问题2???");
        surveys.setSurvey_desc("ttttit");
        surveys.setIspublic(true);
        surveys.setTotal(12);
        surveys.setType("生活");
        surveys.setState(0);
        Date date = new Date();
        System.out.println(date.getTime());
        //SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        surveys.setAdd_time(new java.sql.Date(date.getTime()));
        this.surveysReposity.save(surveys);
    }
    @Test
    public void tsetSaveQuestion(){
        Questions questions = new Questions();
        questions.setQues_tex("奇葩问题8");
        questions.setQues_type(1);
        Surveys s= this.surveysReposity.findById(1).get();
        questions.setSurveys(s);
        Questions question_res = this.questionsRepository.save(questions);
        System.out.println(question_res);
        //Set<Selects> selects = new HashSet<>();
        for (int i=0;i<4;i++){
            Selects sel = new Selects();
            sel.setSel_text("asdas"+i);
            sel.setQuestions(question_res);
            //question_res.getSelectsSet().add(sel);
            this.selectsRepository.save(sel);
            //selects.add(sel);
        }
        //questions.setSelectsSet(selects);
//        s.setId(1);
//        s.getQuestionsSet().add(questions);
//        System.out.println(s);


    }
   // @Test
//    public void testsavesecl(){
//        Surveys s= this.surveysReposity.findById(1).get();
//        System.out.println(s);
//        List<Questions> questions = this.questionsRepository.findQuestionsBySurveys(s, Sort.by(Sort.Direction.DESC,"id"));
//        Character sel[] = new Character[6];
//        sel[0]='A';sel[1]='B';sel[2]='C';sel[3]='D';sel[4]='E';sel[5]='F';
//        for (Questions q:questions) {
//            if (!(this.selectsRepository.findSelectsByQuestions(q).isEmpty()))
//                continue;
//            for (int i=0;i<4;i++){
//                Selects selects = new Selects();
//                selects.setSel_text("奇葩问题xxxxxxxx选啥"+sel[i]);
////                selects.setSel_index(sel[i]);
//                selects.setQuestions(q);
//                q.getSelectsSet().add(selects);
//
//                this.selectsRepository.save(selects);
//
//            }
//        }
//        System.out.println(questions);
//    }
    @Test
    public void testfindSurvey(){
        Surveys surveys = this.surveysReposity.findById(18).get();
        System.out.println(surveys);
        List<Questions> questionsList = this.questionsRepository.findQuestionsBySurveys(surveys,Sort.by(Sort.Direction.DESC,"id"));
        System.out.println(JSON.toJSONString(questionsList));
        List<String> res = new ArrayList<>();
        //Map<Questions,List<String>> questionsListMap = new HashMap<>();
        for (Questions q : questionsList){
//            System.out.println(q.toString());
//            res.add(q.toString());
            q.setSurveys(null);
         }
        System.out.println(questionsList);
        //String s2= JSON.toJSONString(questionsListMap);
        //System.out.println(s2);
    }
    @Test
    public void testsaveres(){
        Selects selects = this.selectsRepository.findById(76).get();
        Integer i =selects.getCount_total();
        selects.setCount_total(++i);
        this.selectsRepository.save(selects);
    }
    @Test
    public void tt(){
        Map<String,String> map = new HashMap<>();
        map.put("ss","ok");
        map.put("asd","sads");
        map.put("ss2","no ok");
        for(String s : map.keySet()){
            System.out.println(map.get(s));
        }
        System.out.println(map);
    }

}