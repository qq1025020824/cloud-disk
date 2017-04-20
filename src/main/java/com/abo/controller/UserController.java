package com.abo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abo.model.User;
import com.abo.service.UserService;
import com.sun.tools.javac.util.Log;


@Controller
public class UserController extends BaseController{
	@Autowired
    private UserService userService;
	
	//用户注册
	//
	@RequestMapping(value = "/register")
	public String showRegisterPage(){
		return "user/register";
	}
	//注册
	@RequestMapping(value = "/doRegister",method = RequestMethod.POST)
	public String doRegister(@RequestParam String phone,@RequestParam String password,@RequestParam String username){
//		System.out.println("into doRegister()");
		//验证
		if(userService.isPhoneUsed(phone)){
			sendMessage("您已经注册过账户，请直接登录。");
//			System.out.println("您已经注册过账户，请直接登录。");
			return "redirect:/login";
		}
//		System.out.println("finish 验证");
		//新增用户
		User user=new User();
		user.setPhone(phone);
		user.setPassword(password);
		user.setUsername(username);
		if(userService.createUser(user)){
			sendMessage("注册成功，请登录。");
//			System.out.println("注册成功，请登录。");
			return "redirect:/login";
		}else{
			sendMessage("系统故障，请稍后再试。");
//			System.out.println("系统故障，请稍后再试。");
			return "redirect:/register";
		}
	}

//	@RequestMapping(value = "/login")
}
