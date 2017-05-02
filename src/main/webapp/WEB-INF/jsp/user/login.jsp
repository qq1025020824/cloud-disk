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
		<h3 class="login-title">立即登录，开始使用网盘</h3>
		<form action="<%=request.getContextPath()%>/doLogin" method="post"
			id="login-form" class="form-horizontal">
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
				<button type="submit" id="submit">登录</button>
			</div>
			<div class="form-group">
				<a href="<%=request.getContextPath()%>/register">没有账号，立即免费注册</a>
			</div>
		</form>
	</div>
	<script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/jquery.validate.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/messages_zh.min.js"></script>
	<script type="text/javascript">
	$().ready(function() {
	// 在键盘按下并释放及提交后验证提交表单
	  $("#login-form").validate({
		    rules: {
		      phone: "required",
		      password: "required",
		    },
		    messages: {
		      phone: "请输入手机",
		      password: "请输入密码",
		    }
		});
	});
	</script>
	<script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
</body>
</html>