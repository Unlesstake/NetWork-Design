package com.cn.controller;


import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.cn.dao.*;
import com.cn.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import net.sf.json.JSONObject;

@Controller
public class LoginController {
	@Resource
	UserDao userdao;

	@Resource
	AdminDao admindao;

	@Resource
	StoreDao storedao;

	@Resource
	SalesDao salesdao;

	@Resource
	RecordDao recorddao;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping("")
	public String login() {
		return "login";
	}

	@PostMapping(value = "/login")
	@ResponseBody
	public String login(@RequestBody CommonUser user, HttpServletResponse response) {
//		注意前台input属性的name一定要与实体类相对应
		if(user.getRole().equals("用户")){		/*当前端选择用户时，则在用户数据库中进行查询*/
			UserInfo exist = userdao.login(user);
			if (exist == null) {
				JSONObject object = new JSONObject();
				object.put("code", 400);
				object.put("desc", "用户名或密码错误！");

				return object.toString();
			}
			JSONObject object = new JSONObject();

			// token是需要加密的，防止cookie被拦截，信息泄露
			int token_user = exist.getId() + 10086;
			Cookie cookie = new Cookie("token_user",String.valueOf(token_user));
			response.addCookie(cookie);

			HttpSession session = request.getSession();
			session.setAttribute(String.valueOf(token_user), exist);

			object.put("code", 200);
			object.put("role","用户");
			object.put("userinfo", exist);
			object.put("desc", "请求成功！");
			return object.toString();
		}else {									/*否则，在管理员中进行查询*/
			Admin exist = admindao.login(user);
			if (exist == null) {
				JSONObject object = new JSONObject();
				object.put("code", 400);
				object.put("desc", "用户名或密码错误！");

				return object.toString();
			}
			JSONObject object = new JSONObject();

			// token是需要加密的，防止cookie被拦截，信息泄露
			int token_admin = exist.getId() + 10010;
			Cookie cookie = new Cookie("token_admin",String.valueOf(token_admin));
			response.addCookie(cookie);

			HttpSession session = request.getSession();
			session.setAttribute(String.valueOf(token_admin), exist);

			object.put("code", 200);
			object.put("role","管理员");
			object.put("admininfo", exist);
			object.put("desc", "请求成功！");
			return object.toString();
		}

	}

	/**
	 * 从cookie中获取当前登录的用户信息，并返回
	 */
	@RequestMapping("/UserInfo")
	public String getUserInfo(Model model) {

//		List<Store> StoreList = storedao.FindAll();
		List<StoreAndSales> StoreList = storedao.StoreAndSales();
		model.addAttribute("StoreList", JSON.toJSONString(StoreList));

		// 从浏览器中获取cookie
		Cookie[] cookies = request.getCookies();
		HttpSession session = request.getSession();

		for (Cookie cookie : cookies) {
			//本程序约定了：名称为 token_user 的 cookie 为用户登录信息的 cookie
			if ("token_user".equals(cookie.getName())) {
				/*
					cookie中，存入数据的格式：
						key： "token"
						value : 加密后的id

					session中，存入数据的格式：
						key : 加密后的id
						value : 用户信息
				 */
				UserInfo user = (UserInfo) session.getAttribute(cookie.getValue());
				model.addAttribute("user",user);
				return "user/index";
			}
		}
		return "public/false";
	}

	@RequestMapping("/AdminInfo")
	public String getAdminInfo(Model model){
		// 从浏览器中获取cookie
		Cookie[] cookies = request.getCookies();

		HttpSession session = request.getSession();

		for (Cookie cookie : cookies) {
			//本程序约定了：名称为 token_admin 的 cookie 为管理员登录信息的 cookie
			if ("token_admin".equals(cookie.getName())) {
				/*
					cookie中，存入数据的格式：
						key： "token"
						value : 加密后的id

					session中，存入数据的格式：
						key : 加密后的id
						value : 用户信息
				 */
				Admin user = (Admin) session.getAttribute(cookie.getValue());
				model.addAttribute("user",user);
				return "user/AdminIndex";
			}
		}
		return "public/false";
	}

	@RequestMapping(path = "/VisualizationDetail", method = RequestMethod.GET)
	public String VisualizationDetail(@RequestParam("id") Integer id,Model model){
		Sales sales = salesdao.find(id);
		Store store = storedao.FindById(id);
		List<SalesRecord> RecordList = recorddao.find(id);
		model.addAttribute("sales",sales);
		model.addAttribute("store",store);
		model.addAttribute("record",JSON.toJSONString(RecordList));
		return "user/VisIndex";
	}



}
