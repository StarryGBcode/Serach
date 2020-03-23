package com.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.Map;
@Controller
public class Hellorworld {
    @RequestMapping("/hello")
    @ResponseBody
    public Map<String,Object> sayhelloworld(){
//        int i=10/0;
        Map<String,Object> msg =new HashMap<>();
        msg.put("msg","helloworld");
        return msg;
    }
}
