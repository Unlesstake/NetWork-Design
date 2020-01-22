package com.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

	@RequestMapping("/jump")
	public String jump(String page) {
		return page;

	}

}
