package com.cn.controller;

import com.alibaba.fastjson.JSON;
import com.cn.dao.QuestionsRepository;
import com.cn.dao.SelectsRepository;
import com.cn.dao.SurveysReposity;
import com.cn.pojo.Questions;
import com.cn.pojo.Selects;
import com.cn.pojo.Surveys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SurveyController {

    @Autowired
    private SurveysReposity surveysReposity;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private SelectsRepository selectsRepository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/survey/{survey_id}")
    public String getSurvey(@PathVariable("survey_id") Integer id){
        Surveys surveys = this.surveysReposity.findById(1).get();
        List<Questions> questionsList = this.questionsRepository.findQuestionsBySurveys(surveys, Sort.by(Sort.Direction.DESC,"id"));
        Map<Questions, List<Selects>> questionsListMap = new HashMap<>();
        for (Questions q : questionsList){
            questionsListMap.put(q,this.selectsRepository.findSelectsByQuestions(q));
        }
        String s2= JSON.toJSONString(surveys);
        System.out.println(s2);
        return s2;
    }
    @PostMapping("/savesurvey")
    public String saveSurvey(@RequestBody Surveys o,HttpSession session){
        System.out.println(o);
        o.setAdd_time(new Date(new java.util.Date().getTime()));
        Surveys surveys_res = this.surveysReposity.save(o);
        System.out.println(surveys_res);
        session.setAttribute("survey_id",surveys_res.getId());
        //String survey_res = JSON.toJSONString(surveys_res);
        Map<String,String> res = new HashMap<>();
        res.put("survey_id", String.valueOf(surveys_res.getId()));
        res.put("msg","succes");
        String resMap = JSON.toJSONString(res);
        return resMap;
    }
    @PostMapping("/saveQuestionToSession")
    public String saveQuestion(@RequestBody Questions questions,HttpSession session){
        session.setAttribute("question",questions);
        System.out.println("saveQ::"+session.getAttribute("questionsListMap"));
        //Map<Questions,List<Selects>> questionsListMap = new HashMap<>();
//        if (null!=session.getAttribute("questionsListMap")){
//            questionsListMap = (Map<Questions, List<Selects>>) session.getAttribute("questionsListMap");
//        }
//        //questionsListMap.put(questions,null);
//        session.setAttribute("questionsListMap",questionsListMap);
        return "succes";
    }
    @PostMapping("/saveSelectsToSession")
    public String saveSelects(@RequestBody List<Selects> selects,HttpSession session){
        System.out.println(selects);
        Map<Questions,List<Selects>> questionsListMap = new HashMap<>();
        if (null!=session.getAttribute("questionsListMap") ){
            questionsListMap = (Map<Questions, List<Selects>>) session.getAttribute("questionsListMap");
        }
        Questions questions = (Questions) session.getAttribute("question");
        questionsListMap.put(questions,selects);
        session.setAttribute("questionsListMap",questionsListMap);
        //session.setAttribute("sleects",selects);
        System.out.println(session.getAttribute("questionsListMap"));
        return "succes";
    }
    @GetMapping("/saveall/{subtotal}")
    public String saveAllQuestion(HttpSession session,@PathVariable("subtotal") Integer total){
        //Map<String,String> msg = new HashMap<>();
        Map<String,String> res = new HashMap<>();
        Map<Questions,List<Selects>> questionsListMap = new HashMap<>();
        if (null!=session.getAttribute("questionsListMap") ){
            questionsListMap = (Map<Questions, List<Selects>>) session.getAttribute("questionsListMap");
        }else {
            res.put("msg","error");
            return JSON.toJSONString(res);
        }
        if (total!=questionsListMap.size()) {
            res.put("msg","error");
            return JSON.toJSONString(res);
        }
        Integer s_id = (Integer) session.getAttribute("survey_id");
        Surveys surveys = this.surveysReposity.findById(s_id).get();
        for (Questions questions : questionsListMap.keySet()){
            questions.setSurveys(surveys);
            this.questionsRepository.save(questions);
            for (Selects selects : questionsListMap.get(questions)){
                selects.setQuestions(questions);
                selects.setCount_total(0);
                this.selectsRepository.save(selects);
            }
        }
        surveys.setTotal(questionsListMap.size());
        surveys.setState(1);
        Surveys surveys1 = this.surveysReposity.save(surveys);
        Integer sur_id = surveys1.getId();
        session.setAttribute("survey_id",null);
        res.put("msg","succes");
        res.put("sur_id",Integer.toString(s_id));
        return JSON.toJSONString(res);
    }
    @GetMapping("/findSurvey/{survey_id}")
    public String findSurvey(@PathVariable("survey_id") Integer survey_id){
        Surveys surveys = this.surveysReposity.findById(survey_id).get();
        List<Questions> questionsList = this.questionsRepository.findQuestionsBySurveys(surveys,Sort.by(Sort.Direction.DESC,"id"));
        System.out.println(JSON.toJSONString(questionsList));
        //Map<Questions,List<String>> questionsListMap = new HashMap<>();
        for (Questions q : questionsList){
            System.out.println(q.toString());
            q.setSurveys(null);
        }
        return JSON.toJSONString(questionsList);
    }
    @GetMapping("/findallSurvey")
    public String findallSurvey(){
        List<Surveys> surveys = this.surveysReposity.findAll();
        List<Surveys> sres = new ArrayList<>();
        for (Surveys s : surveys){
            s.setQuestionsSet(null);
            sres.add(s);
        }
        System.out.println(JSON.toJSONString(sres));
        return JSON.toJSONString(sres);
    }
    @GetMapping("/saveRes1/{select_id}")
    public String saveRes1(@PathVariable("select_id") Integer select_id){
        if (null == select_id){
            return "";
        }
        Selects selects = this.selectsRepository.findById(select_id).get();
        Integer i =selects.getCount_total();
        selects.setCount_total(++i);
        this.selectsRepository.save(selects);
        return "";
    }
    @GetMapping("/saveRes2/{select_text}/{questions_id}")
    public String saveRes2(@PathVariable("select_text") String select_text,@PathVariable("questions_id") Integer q_id){
        if (null==select_text){
            return "";
        }
        Questions questions = this.questionsRepository.findById(q_id).get();
        Selects selects = new Selects();
        selects.setSel_text(select_text);
        selects.setQuestions(questions);
        this.selectsRepository.save(selects);
        return "";
    }
    @GetMapping("/del/{sur_id}")
    public String del_survey(@PathVariable("sur_id") Integer sur_id){
        Surveys surveys = this.surveysReposity.findById(sur_id).get();
        this.surveysReposity.delete(surveys);
        return "ok";
    }
    @GetMapping("/test")
    public String testsession(HttpSession session){
        session.setAttribute("sele","aaa");
        return (String) session.getAttribute("sele");
    }
}
