package com.cn.controller;

import com.cn.dao.UserDao;
import com.cn.entity.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/UserManager/Update/{id}")
    public String UpdateIndex(@PathVariable("id") int id,Model model){
        UserInfo user = userdao.findByid(id);
        model.addAttribute("user",user);
        return "user/UserManagerUpdate";
    }
}
