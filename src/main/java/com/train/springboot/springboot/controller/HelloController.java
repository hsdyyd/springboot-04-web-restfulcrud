package com.train.springboot.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;

/**
 * @author yidong
 * @create 2019-01-23-14:31
 */
@Controller
public class HelloController {

    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("hello","hello thymeleaf");
        map.put("tt","<h3>tt</h3>");
        map.put("users", Arrays.asList("Jsp","Freemarker","Thymeleaf"));
        return "success";
    }
}
