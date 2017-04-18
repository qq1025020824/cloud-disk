<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh_cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="<%= request.getContextPath()%>/static/css/base.css"  rel="stylesheet" type="text/css"/>
    <link href="<%= request.getContextPath()%>/static/css/header.css"  rel="stylesheet" type="text/css"/>
    <title>Document</title>
</head>
<body>
<jsp:include page="include/header.jsp"/>
</body>
</html>