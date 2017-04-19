package com.abo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

//控制器基类，用于操作session
public class BaseController {
	@Autowired
	HttpSession session;
	
	//发送消息
	public void sendMessage(String mes){
		session.setAttribute("message", mes);
	}

}
