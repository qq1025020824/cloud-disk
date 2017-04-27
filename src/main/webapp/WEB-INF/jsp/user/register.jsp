<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"
	language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="../include/common-head-import.jsp" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/static/css/form.css">
</head>
<body>
	<jsp:include page="../include/header.jsp" />
	<div class="baseform">
		<h3 class="register-title">注册新用户，获取你的专属网盘</h3>
		<form action="<%=request.getContextPath()%>/doRegister" method="post"
			id="register-form" class="form-horizontal">
			<div class="form-group">
				<label for="phone" class="col-sm-2 control-label">手机：</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="phone" name="phone"
						placeholder="请输入手机">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">密码：</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="password"
						name="password" placeholder="请输入密码">
				</div>
			</div>
			<div class="form-group">
				<label for="username" class="col-sm-2 control-label">用户名：</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="username"
						name="username" placeholder="请输入用户名">
				</div>
			</div>
			<div class="form-group">
				<button type="submit" id="submit">注册</button>
			</div>
			<div class="form-group">
				<a href="<%=request.getContextPath()%>/login">已经有账号，马上登陆</a>
			</div>
		</form>
	</div>
	<script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
</body>
</html>