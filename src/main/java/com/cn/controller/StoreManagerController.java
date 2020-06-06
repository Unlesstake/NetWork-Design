package com.cn.controller;


import com.cn.dao.RecordDao;
import com.cn.dao.SalesDao;
import com.cn.dao.StoreDao;
import com.cn.entity.Admin;
import com.cn.entity.Sales;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StoreManagerController {
    @Resource
    StoreDao storedao;

    @Resource
    RecordDao recorddao;

    @Resource
    SalesDao salesdao;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/StoreManager")                    //主页面
    public String StoreManager(Model model){
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("startIndex",0);
        map.put("pageSize",50);
        List<Store> StoreList = storedao.FindAll(map);
        model.addAttribute("StoreList", StoreList);
        model.addAttribute("page",1);

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
        Sales sales = salesdao.find(id);
        model.addAttribute("sales",sales);

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
        Sales sales = salesdao.find(id);
        model.addAttribute("store",store);
        model.addAttribute("sales",sales);

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
//        int flag2 = storedao.UpdateSale(NewStore);
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
        int flag = storedao.DelStore(DeStore.getId());
        int flag2 = salesdao.delete(DeStore.getId());
        int flag3 = recorddao.delete(DeStore.getId());
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

    @RequestMapping("/StoreManager/Add")                 /*前往新增电商页面*/
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
        int flag = storedao.AddStore(AddStore);
        Sales sales = new Sales();
        sales.setAged(0);
        sales.setMiddle_aged(0);
        sales.setYoung_man(0);
        sales.setPuber(0);
        sales.setUnder_age(0);
        int flag2 = salesdao.add(sales);
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
    }

    @RequestMapping(path = "/StoreManager/QueryStore", method = RequestMethod.GET)        /*查询电商*/
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
        int startIndex = 0;
        NewView.addObject("StoreList", storedao.Query(store_name,city,business_scope,phone,startIndex));
        NewView.addObject("store_name", store_name);
        NewView.addObject("city",city);
        NewView.addObject("business_scope",business_scope);
        NewView.addObject("phone", phone);
        NewView.addObject("page",1);
        return NewView;
    }

    @RequestMapping(path = "/StoreManager/Paging", method = RequestMethod.GET)
    public ModelAndView Paging(@RequestParam("page") Integer index,@RequestParam("store_name") String store_name, @RequestParam("city") String city, @RequestParam("business_scope") String business_scope, @RequestParam("phone") String phone){
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
        int startIndex = (index-1)*50;
        NewView.addObject("StoreList", storedao.Query(store_name,city,business_scope,phone,startIndex));
        NewView.addObject("store_name",store_name);
        NewView.addObject("city",city);
        NewView.addObject("business_scope",business_scope);
        NewView.addObject("phone",phone);
        NewView.addObject("page",index);
        return NewView;
    }
}
