package com.cn.controller;


import com.cn.dao.RecordDao;
import com.cn.dao.SalesDao;
import com.cn.entity.Admin;
import com.cn.entity.Sales;
import com.cn.entity.SalesRecord;
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
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
public class RecordController {
    @Resource
    RecordDao recorddao;

    @Resource
    SalesDao salesdao;

    @Autowired
    private HttpServletRequest request;


    @RequestMapping(path = "/StoreManager/RecordDetails" , method = RequestMethod.GET)
    public String GoDetails(@RequestParam("store_id") Integer store_id, Model model){
        List<SalesRecord> RecordList = recorddao.find(store_id);
        Integer id = store_id;
        if(RecordList.size()==0){
            SalesRecord salesRecord = new SalesRecord();
            salesRecord.setStoreId(-1);
            RecordList.add(salesRecord);
        }
        model.addAttribute("RecordList",RecordList);
        model.addAttribute("id",id);
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token_admin".equals(cookie.getName())) {
                Admin user = (Admin) session.getAttribute(cookie.getValue());
                model.addAttribute("UserName",user.getUsername());
                break;
            }
        }
        return "user/RecordDetails";
    }

    @PostMapping("/RecordDelete")
    @ResponseBody
    public String RecordDelete(@RequestParam("recordIds[]")Integer [] recordIds){
        List<Integer> recordIdList = Arrays.asList(recordIds);
        int store_id = recordIdList.get(0);
        recordIdList.set(0,-1);
        int flag = recorddao.deleteById(recordIdList);
        if(flag>0){
            List<SalesRecord> RecordList = recorddao.find(store_id);
            int under_age = 0;
            int puber = 0;
            int young_man = 0;
            int middle_aged = 0;
            int aged = 0;
            for (int i = 0; i < RecordList.size(); i++) {
                int age = RecordList.get(i).getBuyerAge();
                int number = RecordList.get(i).getNumber();
                if (age < 18) {
                    under_age = under_age + number;
                } else if (age >= 18 && age <= 28) {
                    puber = puber + number;
                } else if (age >= 29 && age <= 40) {
                    young_man = young_man + number;
                } else if (age >= 41 && age <= 65) {
                    middle_aged = middle_aged + number;
                } else {
                    aged = aged + number;
                }
            }
            Sales sales = new Sales();
            sales.setId(store_id);
            sales.setUnder_age(under_age);
            sales.setPuber(puber);
            sales.setYoung_man(young_man);
            sales.setMiddle_aged(middle_aged);
            sales.setAged(aged);
            int flag2 = salesdao.update(sales);

            JSONObject object = new JSONObject();
            object.put("code",200);
            object.put("desc","删除成功！");
            return object.toString();
        }
        JSONObject object = new JSONObject();
        object.put("code",500);
        object.put("desc","删除失败！");
        return object.toString();
    }

    @RequestMapping(path = "/StoreManage/QueryRecord", method = RequestMethod.GET)        /*查询电商*/
    public ModelAndView Query(@RequestParam("store_id") Integer store_id, @RequestParam("goods_name") String goods_name, @RequestParam("sale_number") Integer sale_number,@RequestParam("deal_time") String deal_time){
        ModelAndView NewView = new ModelAndView("user/RecordDetails");
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie cookie : cookies) {
            if ("token_admin".equals(cookie.getName())) {
                Admin user = (Admin) session.getAttribute(cookie.getValue());
                NewView.addObject("UserName", user.getUsername());
                break;
            }
        }
        List<SalesRecord> RecordList = recorddao.Query(store_id,goods_name,sale_number,deal_time);
        Integer id = store_id;
        if (RecordList.size()==0){
            SalesRecord salesRecord = new SalesRecord();
            salesRecord.setStoreId(-1);
            RecordList.add(salesRecord);
        }
        NewView.addObject("RecordList", RecordList);
        NewView.addObject("id",id);
        NewView.addObject("goods_name",goods_name);
        NewView.addObject("sale_number", sale_number);
        NewView.addObject("deal_time",deal_time);
        return NewView;
    }

}
