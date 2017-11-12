package com.zhaolong.wsn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaolong.wsn.service.PersonService;


@Controller
public class MainController {
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		System.out.println("I am stupid ok ok oko ko ko ko ko ko k ok o ko ko kok o");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "test";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws IOException {
//		Enumeration<String> paramNames = httpServletRequest.getParameterNames();// 获取所有的参数名
//		while (paramNames.hasMoreElements()) {
//			String name = paramNames.nextElement();// 得到参数名
//			String value = httpServletRequest.getParameter(name);// 通过参数名获取对应的值
//			System.out.println(MessageFormat.format("{0}={1}", name, value));
//		}
//		System.out.println(httpServletRequest.getParameter("username"));
		PrintWriter pWriter = httpServletResponse.getWriter();
		pWriter.println(httpServletRequest.getParameter("username"));
	}
	
	@RequestMapping(value = "savePerson", method = RequestMethod.GET)
    @ResponseBody
    public String savePerson(){
        personService.savePerson();
        return "success!";
    }
}
