<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.abo.vo.*"%>
<header class="header">
	<div class="navbar-header">
		<div class="navbar-brand">
			<img src="<%=request.getContextPath()%>/static/img/logo.jpg"
				alt="网络硬盘">
		</div>
	</div>
	<div class="navbar-body">
		<%--声明成员变量 --%>
		<%!UserInfoVO uif;%>
		<%!String mes;%>
		<%--从session中或取变量值 --%>
		<%
			mes = (String) session.getAttribute("message");
			session.removeAttribute("message");
			uif = (UserInfoVO) session.getAttribute("userinfo");
		%>

		<%--展示userinfo --%>
		<div>
			<%
				if (uif != null) {
					String percent = uif.getPercentage();
					String uname = uif.getUsername();
					String usedsize = uif.getUsedsize();
					String freesize = uif.getFreesize();
			%>

			<div>
				<!-- 比例条 -->
				<div class="perbar">
					<span style="width:<%=percent%>"><%=percent%></span>
				</div>

				<!-- 用户按钮 -->
				<div>
					<p><%=uname%></p>
				</div>
			</div>

			<!-- 用户悬浮菜单 -->
			<div>
				<ul>
					<li>已用:<%=usedsize%></li>
					<li>可用:<%=freesize%></li>
					<li>登出</li>
				</ul>
			</div>

			<%
				}
			%>

			<%-- 展示message --%>
			<%
				if (mes != null) {
			%>
			<!-- 通知消息 -->
			<p><%=mes%></p>
			<%
				}
			%>

		</div>

	</div>
</header>


