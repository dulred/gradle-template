package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/tt")
    public String aspectTest(String name ,String password){
        String str = "cccc";
        if (name.equals("ccc")){
            throw  new RuntimeException("你是猪");
        }

        return str;
    }

}
