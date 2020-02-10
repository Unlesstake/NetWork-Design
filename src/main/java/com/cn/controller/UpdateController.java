package com.cn.controller;


import com.cn.dao.UserDao;
import com.cn.entity.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UpdateController {
    @Resource
    UserDao userdao;


    @PostMapping("/update")
    @ResponseBody
    public String update(@RequestBody UserInfo NewUser){
        int flag = userdao.updateUser(NewUser);
        if(flag>0){
            JSONObject object = new JSONObject();
            object.put("code",200);
            object.put("desc","修改成功！");
            return object.toString();
        }
        JSONObject object = new JSONObject();
        object.put("code",400);
        object.put("desc","修改失败！");
        return object.toString();
    }
}
