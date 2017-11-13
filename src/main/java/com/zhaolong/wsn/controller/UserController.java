package com.zhaolong.wsn.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaolong.wsn.entity.Person;
import com.zhaolong.wsn.service.PersonService;

@Controller
public class UserController {
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register() {
		System.out.println("user register mapping!");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "register";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String telephone = request.getParameter("telephone");
		String address = request.getParameter("address");
		Person person = new Person();
        person.setUsername(username);
        person.setPassword(password);
        person.setPhone(telephone);
        person.setAddress(address);
        
        System.out.println("zhaolong_debug_000");
		System.out.println(person.getUsername());
        personService.register(person);
        response.setStatus(200);
		PrintWriter pWriter = response.getWriter();
		pWriter.println("success");
    }
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		System.out.println("user login mapping!");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "login";
	}
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
			throws IOException {
		Person person = personService.login(request.getParameter("username"), request.getParameter("password"));
		System.out.println(person);
		if(person == null){
			response.setStatus(401);
		}else{
			response.setStatus(200);
			httpSession.setAttribute("person", person);
		}
	}
}
