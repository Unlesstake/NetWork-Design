package com.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonInfoController {
    @RequestMapping("/GoAdminIndex")
    public String go(){
        return "user/AdminIndex";
    }
}
