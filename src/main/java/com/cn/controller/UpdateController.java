package com.cn.controller;


import com.cn.dao.AdminDao;
import com.cn.dao.UserDao;
import com.cn.entity.UserInfo;
import com.cn.entity.Admin;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UpdateController {
    @Resource
    UserDao userdao;
    @Resource
    AdminDao admindao;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/update")
    @ResponseBody
    public String update(@RequestBody UserInfo NewUser){
        UserInfo olduser = userdao.findByUsername(NewUser.getUsername());
        boolean change = false;
        if(!olduser.getPassword().equals(NewUser.getPassword())){
            change = true;
        }
        int flag = userdao.updateUser(NewUser);
        if(flag>0){
            JSONObject object = new JSONObject();
            object.put("code",200);
            object.put("desc","修改成功！");
            object.put("change",change);

            HttpSession session = request.getSession();
            int token = olduser.getId() + 10086;            //与登录时的key加密方式一致
            session.setAttribute(String.valueOf(token),NewUser);
            return object.toString();
        }
        JSONObject object = new JSONObject();
        object.put("code",400);
        object.put("desc","修改失败！");
        object.put("change",change);
        return object.toString();
    }

    @PostMapping("/AdminUpdate")
    @ResponseBody
    public String admin_update(@RequestBody Admin NewUser){
        Admin olduser = admindao.findByUsername(NewUser.getUsername());
        boolean change = false;
        if(!olduser.getPassword().equals(NewUser.getPassword())){
            change = true;
        }
        int flag = admindao.updateUser(NewUser);
        if(flag>0){
            JSONObject object = new JSONObject();
            object.put("code",200);
            object.put("desc","修改成功！");
            object.put("change",change);

            HttpSession session = request.getSession();
            int token = olduser.getId() + 10086;            //与登录时的key加密方式一致
            session.setAttribute(String.valueOf(token),NewUser);
            return object.toString();
        }
        JSONObject object = new JSONObject();
        object.put("code",400);
        object.put("desc","修改失败！");
        object.put("change",change);
        return object.toString();
    }
}
