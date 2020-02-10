package com.cn.controller;

import com.cn.dao.UserDao;
import com.cn.entity.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import net.sf.json.JSONObject;

@Controller
public class RegisterController {
    @Resource
    UserDao userdao;
    @RequestMapping("/GoRegister")
    public String goregister(){
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody UserInfo user) {
        UserInfo exist = userdao.isexist(user.getUsername());
        if (exist == null) {
            int flag = userdao.adduser(user);
            if (flag > 0) {
                JSONObject object = new JSONObject();
                object.put("code", 200);
                object.put("desc", "注册成功！");
                return object.toString();
            }
        }
        JSONObject object = new JSONObject();
        object.put("code", 400);
        object.put("desc", "注册失败，该用户名已存在！");
        return object.toString();

    }
}

