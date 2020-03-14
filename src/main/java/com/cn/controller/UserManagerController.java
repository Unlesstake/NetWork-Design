package com.cn.controller;

import com.cn.dao.UserDao;
import com.cn.entity.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserManagerController {
    @Resource
    UserDao userdao;


    @RequestMapping("/UserManager")
    public String manager(Model model){
        List<UserInfo> UserList = userdao.findall();
        model.addAttribute("UserList",UserList);
        return "user/UserManager";
    }
}
