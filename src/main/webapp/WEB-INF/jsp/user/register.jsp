<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="../include/common-head-import.jsp"/>
<%--     <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/font-end-header@1.0.0.css">  --%>
</head>
<body>
<div>
        <h3 class="register-title">注册新用户，获取你的专属网盘</h3>
        <form action="<%= request.getContextPath()%>/doRegister" method="post" id="register-form">
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
                    <label for="username">用户名：</label>
                    <input type="text" name="username" id="username" placeholder="请输入用户名">
                </li>
                <li>
                    <input type="submit" value="注册" id="submit"/>
                </li>
                <li>
                    <a href="<%=request.getContextPath()%>/login" >已经有账号，马上登陆</a>
                </li>
            </ul>
        </form>
</div>
</body>
</html>