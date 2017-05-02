package com.abo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class UserFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Object obj=request.getSession().getAttribute("userinfo");
		if(obj==null){
			filterChain.doFilter(request, response);
		}else{
			response.sendRedirect("file/view?folder=home");
		}
	}

}
