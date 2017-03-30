<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="header">
    <nav>
        <div class="navbar-header">
            <div class="navbar-brand">
                <img src="<%= request.getContextPath()%>/static/img/logo.jpg" alt="问卷投票系统">
            </div>
        </div>
        <div class="navbar-body">
            <ul class="navbar navbar-left">
                <li><a href="/" class="active">首页</a></li>
                <li><a href="#">免费服务</a></li>
                <li><a href="#">企业服务</a></li>
            </ul>
            <ul class="navbar navbar-right">
                <c:if test="${empty userId}" var="isLogout">
                    <li><a href="" class="btn btn-register">注册</a></li>
                    <li><a href="" class="btn btn-login">登录</a></li>
                </c:if>
                <c:if test="${!isLogout}">
                    <li><a href="" class="btn btn-register">进入后台管理系统</a></li>
                </c:if>
            </ul>
        </div>
    </nav>
</header>


