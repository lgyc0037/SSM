<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>出错啦！</h1>
局部异常：
<h1>${err1}</h1>
全局异常
<h1>${exception.message}</h1>
<a href="${pageContext.request.contextPath }/toLogin">返回</a>
</body>
</html>