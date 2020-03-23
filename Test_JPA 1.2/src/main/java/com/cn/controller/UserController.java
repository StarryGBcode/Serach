package com.cn.controller;

import com.alibaba.fastjson.JSON;
import com.cn.dao.QuestionsRepository;
import com.cn.dao.SelectsRepository;
import com.cn.dao.SurveysReposity;
import com.cn.dao.UsersRepository;
import com.cn.pojo.Questions;
import com.cn.pojo.Selects;
import com.cn.pojo.Surveys;
import com.cn.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SurveysReposity surveysReposity;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private SelectsRepository selectsRepository;
    @GetMapping("/alluser")
    public String findall(){
        List<Users> list= this.usersRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        System.out.println(list);
        String s= JSON.toJSONString(list);
        return s;
    }
    @GetMapping("/getuserbypage/{page}/{size}")
    public String getUserByPage(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"id"));
        String s = JSON.toJSONString(this.usersRepository.findAll(pageable));
        return s;
    }
    @PostMapping("/saveuser")
    public String saveUser(@RequestBody Users users){
        Map<String,String> res = new HashMap<>();
        if (this.usersRepository.findByUsername(users.getUsername())!=null){
            res.put("error","用户名被占用，请用其他的名字");
            return JSON.toJSONString(res);
        }
        try {
            users.setAdd_time(new Date());
            users.setPassword(DigestUtils.md5DigestAsHex(users.getPassword().getBytes()));
            Users u = this.usersRepository.save(users);
            if (u.getId() != null){
                res.put("succes","注册成功");
                return JSON.toJSONString(res);
            }else{
                res.put("error","注册失败");
                return JSON.toJSONString(res);
            }
        }catch (ConstraintViolationException e ){
            res.put("error","用户名密码不能为空");
            return JSON.toJSONString(res);
        }

    }
    @PostMapping("/login")
    public String userLogin(@RequestBody Users users, HttpSession session){
        Map<String,String> res = new HashMap<>();
        Users user = this.usersRepository.findByUsername(users.getUsername());
        if (user!=null){
            if (user.getPassword().equals(DigestUtils.md5DigestAsHex(users.getPassword().getBytes()))){
                if (session.getAttribute("user")!=null && user.equals((Users) session.getAttribute("user"))){
                    res.put("succes","登录成功");
                    return JSON.toJSONString(res);
                }
                session.setAttribute("user",user);
                System.out.println(session.getAttribute("user"));
                res.put("succes","登录成功");
                return JSON.toJSONString(res);
            } else {
                res.put("error","密码错误");
                return JSON.toJSONString(res);
            }
        } else {
            res.put("error","用户名有误");
            return JSON.toJSONString(res);
        }


    }
    @GetMapping("/isLogin")
    public String isLogin(HttpSession session){
        Map<String,String> res = new HashMap<>();
        try {
            if (session.getAttribute("user")!=null){
                System.out.println(session.getAttribute("user"));
                res.put("succes","已经登录");
                return JSON.toJSONString(res);
            }else {
                res.put("error","未登录，请先登录");
                return JSON.toJSONString(res);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        res.put("error","系统错误");
        return JSON.toJSONString(res);
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        Map<String,String> res = new HashMap<>();
        try {
            if (session.getAttribute("user")!=null){
                session.removeAttribute("user");
                res.put("succes","已经注销");
                return JSON.toJSONString(res);
            }else {
                res.put("succes","未登录，请先登录");
                return JSON.toJSONString(res);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        res.put("error","系统错误");
        return JSON.toJSONString(res);
    }
//    @GetMapping("/survey/{survey_id}")
//    public String getSurvey(@PathVariable("survey_id") Integer id){
//        Surveys surveys = this.surveysReposity.findById(1).get();
//        List<Questions> questionsList = this.questionsRepository.findQuestionsBySurveys(surveys,Sort.by(Sort.Direction.DESC,"id"));
//        Map<Questions, List<Selects>> questionsListMap = new HashMap<>();
//        for (Questions q : questionsList){
//            questionsListMap.put(q,this.selectsRepository.findSelectsByQuestions(q));
//        }
//        String s2= JSON.toJSONString(questionsListMap);
//        System.out.println(s2);
//        return s2;
//    }
}
