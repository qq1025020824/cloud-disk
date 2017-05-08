<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"
	language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.*"%>
<%@page import="com.abo.vo.*"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="../include/common-head-import.jsp" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/view.css">
</head>
<body>
	<jsp:include page="../include/header.jsp" />
	<div>
		<%--声明变量 --%>
		<%!Stack<MyFileVO> paths;%>
		<%!List<MyFileVO> contents;%>
		<%!String folderid; //该目录id%>
		<%--获取资源 --%>
		<%
			paths = (Stack<MyFileVO>) request.getAttribute("paths");
			contents = (List<MyFileVO>) request.getAttribute("contents");
		%>
		<!-- 地址栏 -->
		<ol class="breadcrumb">
			<%--展示地址 --%>
			<%
				MyFileVO mfv;
				String id;
				String name;
				while (paths.size() > 1) {
					mfv = paths.pop();
					id = mfv.getId();
					name = mfv.getName();
			%>
			<li><a
				href="<%=request.getContextPath()%>/file/view?folder=<%=id%>"><%=name%></a></li>
			<%
				}
				if (!paths.empty()) {
					mfv = paths.pop();
					id = mfv.getId();
					name = mfv.getName();
			%>
			<li class="active"><%=name%></li>
			<%
				//更新到此mfv,id,name为该目录
					folderid = id;
				}
			%>
		</ol>

		<!-- 目录内容 -->
		<form action="#" method="post" id="checkfrom">
			<input type="hidden" name="folder" value="<%=folderid%>" />
			<%--展示内容 --%>
			<%
				if (!contents.isEmpty()) {
			%>
			<table class="table table-hover table-striped table-bordered">
				<tr>
					<th></th>
					<th>文件名</th>
					<th>大小</th>
					<th>更新时间</th>
				</tr>
				<%
					for (MyFileVO mfvc : contents) {
							String fid = mfvc.getId();
							String fname = mfvc.getName();
							String ftype = mfvc.getType();
							String fsize = mfvc.getSize();
							String fdate = mfvc.getCreatedate();
				%>
				<tr>
					<td><input type="checkbox" value="<%=fid%>" name="id"
						class="filecheck"></td>
					<%
						if (ftype.equals("folder")) {
					%>
					<td><a
						href="<%=request.getContextPath()%>/file/view?folder=<%=fid%>"><span
							class="glyphicon glyphicon-folder-open" aria-hidden="true"></span><%=fname%></a></td>
					<%
						} else {
					%>
					<td><span class="glyphicon glyphicon-file" aria-hidden="true"></span><%=fname%></td>
					<%
						}
					%>
					<td><%=fsize%></td>
					<td><%=fdate%></td>
				</tr>
				<%
					}
				%>
			</table>
			<%
				}
			%>
		</form>

		<!-- 上传文件 -->
		<div id="uploadfrom" class="hiddenfrom">
			<form method="post" id="theuploadfrom"
				action="#"
				enctype="multipart/form-data">
				<input type="hidden" name="folder" value="<%=folderid%>" />
				<input type="hidden" id="md5input" name="md5" />
				<div class="form-group">
					<label for="exampleInputFile">上传文件</label> <input type="file"
						name="uploadfile" id="exampleInputFile">
					<p class="help-block">请选择要上传的文件</p>
				</div>
				<button type="submit" class="btn btn-default">上传</button>
			</form>
		</div>

		<!-- 新建文件夹 -->
		<div id="mkdirfrom" class="hiddenfrom">
			<form method="post" action="<%=request.getContextPath()%>/file/mkdir">
				<input type="hidden" name="folder" value="<%=folderid%>" />
				<input type="hidden" id="md5input" name="md5" />
				<div class="form-group">
					<label for="InputName">新建文件夹</label> <input type="text" name="name"
						class="form-control" id="InputName" placeholder="新建文件夹">
				</div>
				<button type="submit" class="btn btn-default">确定</button>
			</form>
		</div>

		<!-- 按钮栏 -->
		<div class="buttonbar">
			<button id="upload">上传文件</button>
			<button id="mkdir">新建文件夹</button>
			<button id="download">下载</button>
			<button id="delect">删除</button>
		</div>
	</div>

	<!-- script -->
	<script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/browser-md5-file.min.js"></script>
	<script>
		$(function() {
			//上传表单
			$("#uploadfrom").hide();
			$("#upload").on('click', function() {
				$("#mkdirfrom").hide();
				$("#mkdir").removeClass('onpress');
				$("#uploadfrom").toggle();
				$("#upload").toggleClass('onpress');
			});
			// 新建文件夹表单
			$("#mkdirfrom").hide();
			$("#mkdir").on('click', function() {
				$("#uploadfrom").hide();
				$("#upload").removeClass('onpress');
				$("#mkdirfrom").toggle();
				$("#mkdir").toggleClass('onpress');
			});

			// 控制按钮隐藏
			$("#download").hide();
			$("#delect").hide();
			$(".filecheck").click(function() {
				$(".filecheck").each(function() {
					if ($(this).prop("checked") == true) {
						$("#download").show();
						$("#delect").show();
						return false;
					} else {
						$("#download").hide();
						$("#delect").hide();
						return true;
					}
				})
			})

			// 下载按钮
			$("#download").on('click', function() {
				var path = "<%=request.getContextPath()%>/file/download";
				$('#checkfrom').attr("action", path);
				$('#checkfrom').submit();
			})

			// 删除按钮
			$("#delect").on('click', function() {
				if (confirm("确定删除文件？")) {
					var path = "<%=request.getContextPath()%>/file/delect";
					$('#checkfrom').attr("action", path);
					$('#checkfrom').submit();
				}
			})
			
			//上传 md5
			$('#exampleInputFile').bind('change', function(event) {
				var file = event.target.files[0];
				browserMD5File(file, function(err, md5) {
					$('#md5input').val(md5);
					$.get("<%=request.getContextPath()%>/file/checkmd5",{ md5:md5 },function(data){
						var path;
						if (data == "exist") {
							path="<%=request.getContextPath()%>/file/quickupload";
						} else {
							path="<%=request.getContextPath()%>/file/doupload";
						}
						$('#theuploadfrom').attr("action", path);
					});
				});
			});
		})
	</script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
</body>
</html>