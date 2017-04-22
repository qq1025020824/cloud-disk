package com.abo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.abo.vo.UserInfoVO;

//控制器基类，用于操作session
public class BaseController {
	@Autowired
	HttpSession session;

	// 发送消息
	public void sendMessage(String mes) {
		session.setAttribute("message", mes);
	}
	
	/**
	 * 发送用户信息
	 * @param uif
	 */
	public void sendUserInfo(UserInfoVO uif){
		session.setAttribute("userinfo", uif);
		System.out.println(session.getAttribute("userinfo"));
	}
}
