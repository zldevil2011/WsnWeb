<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人用户登陆</title>
<link href="https://cdn.bootcss.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
<style type="text/css">
	body{
		background:url("/WsnWeb/img/background.png") no-repeat;
		background-size: 100% 100%;
		background-attachment: fixed;
	}
	.login-container{
		min-width: 400px;
		text-align: center;
		margin: 0 auto;
		position: absolute;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
		background: rgba(0,0,0,0.25);
		padding: 20px;
	}
	.input-group{
		padding: 10px 5px;
	}
	.input-group label, .input-group input{
		display: inline-block;
		box-sizing: border-box;
		text-align: center;
	}
	.input-group label{
		width: 30%;
	}
	.input-group input{
		width: 70%;
	}
	.zl-btn{
		margin-top: 10px;
		outline:none;
		border: 0;
		cursor: pointer;
	}
</style>
</head>
<body>
	<div class="login-container">
		<h3>个人用户登陆</h3>
		<div class="input-group">
			<label>用户名</label><input type="text">
		</div>
		<div class="input-group">
			<label>密码</label><input type="password">
		</div>
		<div>
			<button class="zl-btn btn btn-success btn-block">登陆</button>
		</div>
	</div>
	
</body>
</html>