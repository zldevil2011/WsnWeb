package com.zhaolong.wsn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		System.out.println("user login mapping!");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "login";
	}
}
