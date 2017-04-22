<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="../include/common-head-import.jsp"/>
<%--     <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/font-end-header@1.0.0.css">  --%>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<div>
        <h3 class="login-title">立即登录，开始使用网盘</h3>
        <form action="<%= request.getContextPath()%>/doLogin" method="post" id="login-form">
            <ul>
                <li>
                    <label for="phone">手机：</label>
                    <input type="text" name="phone" id="phone" placeholder="请输入手机">
                </li>
                <li>
                    <label for="password">密码：</label>
                    <input type="password" name="password" id="password"
                           placeholder="请输入密码">
                </li>
                <li>
                    <input type="submit" value="登录" id="submit"/>
                </li>
                <li>
                    <a href="<%=request.getContextPath()%>/register" >没有账号，立即免费注册</a>
                </li>
            </ul>
        </form>
</div>
</body>
</html>