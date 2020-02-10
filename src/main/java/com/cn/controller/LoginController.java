package com.cn.controller;


import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.dao.UserDao;
import com.cn.entity.UserInfo;

import net.sf.json.JSONObject;

@Controller
public class LoginController {
	@Resource
	UserDao userdao;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping("")
	public String login() {
		return "login";
	}

	@PostMapping(value = "/login")
	@ResponseBody
	public String login(@RequestBody UserInfo user, HttpServletResponse response) {

//		注意前台input属性的name一定要与实体类相对应
		UserInfo exist = userdao.login(user);
		if (exist == null) {
			JSONObject object = new JSONObject();
			object.put("code", 400);
			object.put("desc", "用户名或密码错误！");

			return object.toString();
		}
		JSONObject object = new JSONObject();

		// token是需要加密的，防止cookie被拦截，信息泄露
		int token = exist.getId() + 10086;
		Cookie cookie = new Cookie("token",String.valueOf(token));
		response.addCookie(cookie);

		HttpSession session = request.getSession();
		session.setAttribute(String.valueOf(token), exist);

		object.put("code", 200);
		object.put("userinfo", exist);
		object.put("desc", "请求成功！");
		return object.toString();
	}

	/**
	 * 从cookie中获取当前登录的用户信息，并返回
	 */
	@RequestMapping("/UserInfo")
	public String getUserInfo(Model model) {

		// 从浏览器中获取cookie
		Cookie[] cookies = request.getCookies();

		HttpSession session = request.getSession();

		for (Cookie cookie : cookies) {
			//本程序约定了：名称为 token 的 cookie 为用户登录信息的 cookie
			if ("token".equals(cookie.getName())) {
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
}
