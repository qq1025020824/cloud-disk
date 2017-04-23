package com.abo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.abo.vo.UserInfoVO;

/**
 * 控制器基类，用于操作session
 * @author abo
 *
 */
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
	
	/**
	 * 获取session中保存的user id
	 * @return 有则返回id，否则返回null
	 */
	public Long getUserID(){
		Long id=null;
		UserInfoVO uif=(UserInfoVO) session.getAttribute("userinfo");
		if(uif!=null){
			id=uif.getId();
		}
		return id;
	}
}
