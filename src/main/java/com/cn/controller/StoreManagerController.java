package com.cn.controller;


import com.cn.dao.StoreDao;
import com.cn.entity.Admin;
import com.cn.entity.Store;
import com.cn.entity.UserInfo;
import net.sf.json.JSONObject;
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
        Store store = storedao.FindSale(id);
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
        Store storeSale = storedao.FindSale(id);
        model.addAttribute("store",store);
        model.addAttribute("storeSale",storeSale);

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
        int flag2 = storedao.UpdateSale(NewStore);
        if(flag>0&&flag2>0){
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
        int flag = storedao.DelStore(DeStore.getId());
        int flag2 = storedao.DelSale(DeStore.getId());
        if(flag>0&&flag2>0){
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

    @PostMapping("/StoreManagerAdd")
    @ResponseBody
    public String StoreManagerAdd(@RequestBody Store AddStore){
//        Store exist = storedao.IsExist(AddStore.getStore_name());
//        if(exist == null){
        int flag = storedao.AddStore(AddStore);
        int flag2 = storedao.AddSale(AddStore);
        if(flag>0&&flag2>0){
            JSONObject object = new JSONObject();
            object.put("code",200);
            object.put("desc","添加电商数据成功！");
            object.put("data", AddStore);
            return object.toString();
        }else {
            JSONObject object = new JSONObject();
            object.put("code",400);
            object.put("desc","添加失败，请稍后重试！");
            return object.toString();
        }

//        }
//        JSONObject object = new JSONObject();
//        object.put("code", 405);
//        object.put("desc", "添加失败，该电商名已存在！");
//        return object.toString();
    }

    @RequestMapping(path = "/QueryStore", method = RequestMethod.GET)        /*查询电商*/
    public ModelAndView Query(@RequestParam("store_name") String store_name, @RequestParam("city") String city, @RequestParam("business_scope") String business_scope, @RequestParam("phone") String phone){
        ModelAndView NewView = new ModelAndView("user/StoreManager");
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token_admin".equals(cookie.getName())) {
                Admin user = (Admin) session.getAttribute(cookie.getValue());
                NewView.addObject("UserName", user.getUsername());
                break;
            }
        }
        NewView.addObject("StoreList", storedao.Query(store_name,city,business_scope,phone));
        NewView.addObject("store_name", store_name);
        NewView.addObject("city",city);
        NewView.addObject("business_scope",business_scope);
        NewView.addObject("phone", phone);
        return NewView;
    }
}
