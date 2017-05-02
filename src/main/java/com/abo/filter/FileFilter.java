package com.abo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class FileFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Object obj=request.getSession().getAttribute("userinfo");
		if(obj==null){
			response.sendRedirect("/cloud_disk/login");
		}else{
			filterChain.doFilter(request, response);
		}
	}
}
