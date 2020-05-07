package Atcrowdfunding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Atcrowdfunding.mapper.TMemberMapper;

@Controller
public class DispatcherController {
	
	@Autowired
	TMemberMapper memberMapper;
	//登录页面的跳转
	@RequestMapping("/login.html")
	public String toLoginPage() {
		//跳转到登录页面
		return "login";
	}
	
	
	//首页的跳转
	@RequestMapping(value= {"/" ,"/index" , "/index.html"})
	public String toIndexPage() {
		System.out.println(memberMapper);
		return "index";
	}
}
