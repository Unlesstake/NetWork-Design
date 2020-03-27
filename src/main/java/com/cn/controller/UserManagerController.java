package com.cn.controller;

import com.cn.dao.UserDao;
import com.cn.entity.Admin;
import com.cn.entity.UserInfo;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserManagerController {
    @Resource
    UserDao userdao;

    @Autowired
    private HttpServletRequest request;


    @RequestMapping("/UserManager")             /*主页面跳转*/
    public String manager(Model model){
        List<UserInfo> UserList = userdao.findall();
        model.addAttribute("UserList",UserList);
        model.addAttribute("UserNumber", UserList.size());

        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token_admin".equals(cookie.getName())) {
                Admin user = (Admin) session.getAttribute(cookie.getValue());
                model.addAttribute("UserName",user.getUsername());
                break;
            }
        }
        return "user/UserManager";
    }

    @RequestMapping("/UserManager/Update/{id}")         /*更新用户的页面*/
    public String UpdateIndex(@PathVariable("id") int id,Model model){
        UserInfo user = userdao.findByid(id);
        model.addAttribute("user",user);
        return "user/UserManagerUpdate";
    }

    @PostMapping("/UserManagerUpdate")                  /*更新用户*/
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

    @PostMapping("/UserManagerDelete")                  /*删除用户*/
    @ResponseBody
    public  String UserManagerDelete(@RequestBody UserInfo DeUser){
        int flag = userdao.delUser(DeUser.getUsername());
        if(flag>0){
            JSONObject object = new JSONObject();
            object.put("code", 200);
            object.put("desc", "删除用户成功！");
            return object.toString();
        }
        JSONObject object = new JSONObject();
        object.put("code", 400);
        object.put("desc", "删除失败，请稍后重试！");
        return object.toString();
    }

    @RequestMapping("/UserManager/Add")                 /*前往新增用户页面*/
    public String GoAdd(){
        return "user/UserManagerAdd";
    }

    @PostMapping("/UserManagerAdd")                     /*新增用户*/
    @ResponseBody
    public String UserManagerAdd(@RequestBody UserInfo AddUser){
        UserInfo exist = userdao.isexist(AddUser.getUsername());
        if(exist == null){
            int flag = userdao.adduser(AddUser);
            if(flag>0){
                JSONObject object = new JSONObject();
                object.put("code",200);
                object.put("desc","添加用户成功！");
                object.put("data", AddUser);
                return object.toString();
            }else {
                JSONObject object = new JSONObject();
                object.put("code",400);
                object.put("desc","添加失败，请稍后重试！");
                return object.toString();
            }

        }
        JSONObject object = new JSONObject();
        object.put("code", 405);
        object.put("desc", "添加失败，该用户名已存在！");
        return object.toString();
    }

    @RequestMapping(path = "/Query", method = RequestMethod.GET)        /*查询用户*/
    public ModelAndView Query(@RequestParam("username") String username,@RequestParam("age") Integer age,@RequestParam("sex") String sex,@RequestParam("phone") String phone){
        ModelAndView NewView = new ModelAndView("user/UserManager");
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token_admin".equals(cookie.getName())) {
                Admin user = (Admin) session.getAttribute(cookie.getValue());
                NewView.addObject("UserName", user.getUsername());
                break;
            }
        }
        NewView.addObject("UserList", userdao.Query(username,age,sex,phone));
        NewView.addObject("username", username);
        NewView.addObject("age",age);
        NewView.addObject("sex",sex);
        NewView.addObject("phone", phone);
        NewView.addObject("UserNumber",userdao.Query(username,age,sex,phone).size());
        return NewView;
    }
}
