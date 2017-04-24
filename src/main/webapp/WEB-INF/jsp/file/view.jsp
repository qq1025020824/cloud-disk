<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"
	language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="../include/common-head-import.jsp" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/view.css">
</head>
<body>
	<jsp:include page="../include/header.jsp" />
	<div>
		<!-- 地址栏 -->
		<div>
			<a href="<%=request.getContextPath()%>/file/view?folder=(id)">(name)</a>
		</div>

		<!-- 目录内容 -->
		<div>
			<form action="#" method="post" id="checkfrom">
				<input type="checkbox" value="1" name="test"> <input
					type="checkbox" value="2" name="test"> <input
					type="checkbox" value="3" name="test">
			</form>
		</div>
		
		<!-- 上传文件 -->
		<div class="uploadfrom">
			<form method="post"
				action="<%=request.getContextPath()%>/file/doupload"
				enctype="multipart/form-data">
				<input type="hidden" name="folder" value="(id)" />
				<input type="file" name="uploadfile" /> 
				<input type="submit" value="上传" />
			</form>
		</div>
		
		<!-- 按钮栏 -->
		<div>
			<button id="upload">上传</button>
			<button id="download">下载</button>
			<button id="delect">删除</button>
			<script src="<%=request.getContextPath()%>/static/js/jquery.min.js" ></script>
			<script>
		    $(function () {
		    	$("#uploadfrom").hide();
		    	$("#upload").on('click',function () {
		    		$("#uploadfrom").toggle();
		    		$("#upload").toggleClass('onpress');
		    	});
		        $("#download").on('click',function () {
		        	var path = "<%=request.getContextPath()%>/file/download";  
		            $('#checkfrom').attr("action", path);
		            $('#checkfrom').submit();
		        })
		        $("#delect").on('click',function () {
		        	if(confirm("确定删除文件？")){
		        		var path = "<%=request.getContextPath()%>/file/delect";
						$('#checkfrom').attr("action", path);
						$('#checkfrom').submit();
					}
				})
			})
			</script>
		</div>
	</div>
</body>
</html>