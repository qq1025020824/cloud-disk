<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.abo.vo.*"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
			<span class="title">网络硬盘</span>
		</div>
		<div class="navbar-body navbar-right">
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
			<%
				if (uif != null) {
					String percent = uif.getPercentage();
					String uname = uif.getUsername();
					String usedsize = uif.getUsedsize();
					String freesize = uif.getFreesize();
			%>
			<!-- 比例条 -->
			<div class="progress">
				<div class="progress-bar" role="progressbar" aria-valuenow="2"
					aria-valuemin="0" aria-valuemax="100"
					style="min-width: 3em; width: <%=percent%>;">
					<%=percent%>
				</div>
			</div>
			<!-- 用户按钮 -->
			<a class="dropdown-toggle" data-toggle="dropdown" href="#"
				role="button" aria-haspopup="true" aria-expanded="false"> <%=uname%>
				<span class="caret"></span>
			</a>
			<!-- 用户悬浮菜单 -->
			<ul class="dropdown-menu list-group">
				<li class="list-group-item">已用:<%=usedsize%></li>
				<li class="list-group-item">可用:<%=freesize%></li>
				<a class="list-group-item"
					href="<%=request.getContextPath()%>/logout">登出</a>
			</ul>
			<%
				}
			%>
		</div>
		<%-- 展示message --%>
		<%
			if (mes != null) {
		%>
		<!-- 通知消息 -->
		<div class="alert alert-warning alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<%=mes%>
		</div>
		<%
			}
		%>
	</div>
</nav>