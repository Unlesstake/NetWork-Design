package com.cn.controller;


import com.cn.dao.StoreDao;
import com.cn.entity.Admin;
import com.cn.entity.Store;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StoreManagerController {
    @Resource
    StoreDao storedao;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/StoreManager")                    //主页面
    public String StoreManager(Model model){
        List<Store> StoreList = storedao.FindAll();
        model.addAttribute("StoreList", StoreList);

        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token_admin".equals(cookie.getName())) {
                Admin user = (Admin) session.getAttribute(cookie.getValue());
                model.addAttribute("UserName",user.getUsername());
                break;
            }
        }
        return "user/StoreManager";
    }

    @RequestMapping("/StoreManager/Details/{id}")                       //查看销售记录详情
    public String Details(@PathVariable("id") int id, Model model){
        Store store = storedao.FindById(id);
        model.addAttribute("store",store);

        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token_admin".equals(cookie.getName())) {
                Admin admin = (Admin) session.getAttribute(cookie.getValue());
                model.addAttribute("UserName",admin.getUsername());
                break;
            }
        }
        return "user/StoreManagerDetails";
    }

    @RequestMapping("/StoreManager/Update/{id}")                        //前往编辑页面
    public String UpdateIndex(@PathVariable("id") int id, Model model){
        Store store = storedao.FindById(id);
        model.addAttribute("store",store);

        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token_admin".equals(cookie.getName())) {
                Admin admin = (Admin) session.getAttribute(cookie.getValue());
                model.addAttribute("UserName",admin.getUsername());
                break;
            }
        }
        return "user/StoreManagerUpdate";
    }

    @PostMapping("/StoreManagerUpdate")                 //更新电商数据
    @ResponseBody
    public String StoreManagerUpdate(@RequestBody Store NewStore){
        int flag = storedao.UpdateStore(NewStore);
        if(flag>0){
            JSONObject object = new JSONObject();
            object.put("code", 200);
            object.put("desc", "修改成功！");
            object.put("data", NewStore);
            return object.toString();
        }
        JSONObject object = new JSONObject();
        object.put("code",400);
        object.put("desc","修改失败，请检查数据后重试！");
        return  object.toString();
    }

    @PostMapping("/StoreManagerDelete")
    @ResponseBody
    public String StoreManagerDelete(@RequestBody Store DeStore){
        int flag = storedao.DelStore(DeStore.getStore_name());
        if(flag>0){
            JSONObject object = new JSONObject();
            object.put("code", 200);
            object.put("desc", "删除电商数据成功！");
            return object.toString();
        }
        JSONObject object = new JSONObject();
        object.put("code", 400);
        object.put("desc", "删除失败，请稍后重试！");
        return object.toString();
    }

    @RequestMapping("/StoreManager/Add")                 /*前往新增用户页面*/
    public String GoAdd(Model model){
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token_admin".equals(cookie.getName())) {
                Admin admin = (Admin) session.getAttribute(cookie.getValue());
                model.addAttribute("UserName",admin.getUsername());
                break;
            }
        }
        return "user/StoreManagerAdd";
    }

    @PostMapping("StoreManagerAdd")
    @ResponseBody
    public String StoreManagerAdd(@RequestBody Store AddStore){
        return "user";
    }
}
