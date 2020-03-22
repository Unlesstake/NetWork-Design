package com.cn.controller;

import com.cn.dao.UserDao;
import com.cn.entity.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/UserManagerUpdate")
    @ResponseBody
    public String UserManagerUpdate(@RequestBody UserInfo NewUser){
        int flag = userdao.updateUser(NewUser);
        if(flag>0){
            JSONObject object = new JSONObject();
            object.put("code", 200);
            object.put("desc", "修改成功！");
            object.put("data", NewUser);
            return object.toString();
        }
        JSONObject object = new JSONObject();
        object.put("code",400);
        object.put("desc","修改失败，请检查数据后重试！");
        return  object.toString();
    }
}
