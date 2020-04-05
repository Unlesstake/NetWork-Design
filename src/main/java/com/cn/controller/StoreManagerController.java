package com.cn.controller;


import com.cn.dao.StoreDao;
import com.cn.entity.Admin;
import com.cn.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/StoreManager/Update/{id}")
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
}
