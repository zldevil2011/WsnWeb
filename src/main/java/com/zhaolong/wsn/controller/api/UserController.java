package com.zhaolong.wsn.controller.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhaolong.wsn.entity.Person;
import com.zhaolong.wsn.service.PersonService;

@Controller
@RequestMapping(value = "/api/*") 
public class UserController {
	/*
	register：用户注册请求
	login：用户登陆
	 */
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException{

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String telephone = request.getParameter("telephone");
		String address = request.getParameter("address");
		System.out.println(request.getHeader("Content-Type"));
		System.out.println(username);
		System.out.println(password);
		try{
			Person pre = personService.getPerson(username);
			if(pre == null){
				// 没有同名的用户，可以注册
				Person person = new Person();
				person.setUsername(username);
				person.setPassword(password);
				person.setPhone(telephone);
				person.setAddress(address);
				personService.register(person);
				response.setStatus(200);
				PrintWriter pWriter = response.getWriter();
				pWriter.println("success");
			} else {
				response.setStatus(400);
				PrintWriter pWriter = response.getWriter();
				pWriter.println("error");
			}
		}catch (Exception e){
			response.setStatus(500);
			PrintWriter pWriter = response.getWriter();
			pWriter.println("service error");
		}
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
