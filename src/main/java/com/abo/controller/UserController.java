package com.abo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abo.model.User;
import com.abo.service.UserService;
import com.abo.vo.UserInfoVO;
import com.sun.tools.javac.util.Log;


@Controller
public class UserController extends BaseController{
	private static final Logger Log = LoggerFactory.getLogger(UserController.class);
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
		//验证
		if(userService.isPhoneUsed(phone)){
			sendMessage("您已经注册过账户，请直接登录。");
			return "redirect:/login";
		}
		//新增用户
		User user=new User();
		user.setPhone(phone);
		user.setPassword(password);
		user.setUsername(username);
		if(userService.createUser(user)){
			sendMessage("注册成功，请登录。");
			return "redirect:/login";
		}else{
			sendMessage("系统故障，请稍后再试。");
			return "redirect:/register";
		}
	}

	@RequestMapping(value = "/login")
	public String showLoginPage(){
		return "user/login";
	}
	
	@RequestMapping(value = "/doLogin")
	public String doLogin(@RequestParam String phone,@RequestParam String password){
		//验证是否注册
		if(!userService.isPhoneUsed(phone)){
			sendMessage("该账号尚未注册，请先注册。");
			return "redirect:/register";
		}
		
		//验证密码
		User user=new User();
		user.setPhone(phone);
		user.setPassword(password);
		if(userService.loginUser(user)){
			Log.debug("!!user:{}",user);
			//登录成功
			//更新用户信息
			UserInfoVO uif=userService.updateUserInfo(user.getId());
			if(uif!=null){
				sendUserInfo(uif);
				System.out.println(uif.getUsername());
			}
			//跳转到网盘展示页
			return "redirect:/file/view";
		}else{
			sendMessage("密码错误，请重新登录。");
			return "redirect:/login";
		}
	}
}
