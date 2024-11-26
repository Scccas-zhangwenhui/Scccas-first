<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>登录页面</title>
<link href="static/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript">
	function refreshCode(){
		document.getElementById("mycode").src = "validateCode?t=" + Math.random();
	}
</script>
<style>
.welcome-banner {
    width: 100%;
    height: 400px;
    padding-top: 5px;
    background-image: url('./images/5.jpg');
    background-size: cover; /* 确保背景图片覆盖整个区域 */
    background-position: center; /* 将图片居中显示 */
    display: flex;
    justify-content: center;
    align-items: center; /* 垂直居中文本 */
}

.welcome-banner h2 {
    color: white; /* 如果背景较暗，可以设置文本颜色为白色 */
    text-shadow: 1px 1px 2px black; /* 为文本添加阴影以提高可读性 */
}
</style>
</head>
<body class="bg-info">
	<div class="container">
    <div class="welcome-banner">
        <h2 align="center">欢迎使用名片管理系统</h2>
    </div>
</div>

		<br>
		<form:form action="user/login" modelAttribute="myUser" method="post"  cssClass="form-horizontal">
			<div class="form-group has-success">
				<label class="col-sm-2 col-md-2 control-label">用户名</label>
				<div class="col-sm-4 col-md-4">
					<form:input cssClass="form-control" placeholder="请输入您的用户名" path="uname"/>
				</div>
			</div>
			<div class="form-group has-success">
				  <label class="col-sm-2 col-md-2 control-label">密码</label>
				  <div class="col-sm-4 col-md-4">
		  				<form:password cssClass="form-control" placeholder="请输入您的密码" path="upwd"/>
				  </div>
			 </div>
			<div class="form-group has-success">
				  <label class="col-sm-2 col-md-2 control-label">验证码</label>
				  <div class="col-sm-4 col-md-4">
				  		<table style="width: 100%">
				  			<tr>
				  				<td><form:input  cssClass="form-control" placeholder="请输入验证码" path="code"/></td>
					 			<td>
					 				<img src="validateCode" id="mycode">
					 			</td>
					 			<td>
					 				<a href="javascript:refreshCode()">刷新二维码</a>
					 			</td>
				  			</tr>
				  		</table>
				  </div>
			 </div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-success" >登录</button>
					<button type="reset" class="btn btn-primary" >重置</button>
					没账号，请<a href="user/toRegister">注册</a>。
				</div>
			</div>
			${errorMessage}
		</form:form>
		
</body>
</html>