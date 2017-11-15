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

import com.zhaolong.wsn.entity.Person;
import com.zhaolong.wsn.service.PersonService;


@Controller
public class MainController {
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		System.out.println("index tempalte");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "index";
	}	
}
