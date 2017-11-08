<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" value="IE=edge, chrome=1">
	<meta name="viewport" content = "width=device-width, initial-scale=1">
	<title>欢迎您</title>
	<link href="" rel="text/stylesheet">
	<script src="https://unpkg.com/vue"></script>
	<script src="https://cdn.bootcss.com/vue-resource/1.3.4/vue-resource.min.js"></script>
	<style type="text/css">
		body{
			/*background: -webkit-gradient(linear, 0 0, 0 bottom, from(#ff0000), to(rgba(0, 0, 255, 0.5))); */  
			background: -webkit-linear-gradient(right, #ff0000 , rgba(0, 0, 255, 0.5));
		}
		.vertical-horizontal-center{
			position: absolute;
			left: 50%;
			top: 50%;
			transform: translate(-50%, -50%);
		}
		.form-container{
			border: 1px dotted black;
			padding: 10px;
			width: 300px;
		}
		.form-group{
			display: flex;
			justify-content: space-between;
			margin: 10px 0;
		}
		.item{
			flex-grow: 1;
			flex: 1;
			font-size: 12px;
		}
		.text-center{
			text-align:center;
		}
		.btn{
			outline:none;
			border: 0;
			cursor: pointer;
		}
		.btn-submit{
			width: 50%;
			padding: 5px;
			text-align: center;
			background-color: rgba(0, 128, 0, 0.4);
			border-radius: 5px;
		}
		.btn-submit:hover {
            background-color: rgba(0, 128, 0, 0.8);
        }
        .btn-submit:active {
            background-color: #00ff00;
        }
	</style>
</head>
<body>
	<center>
		<h2 style="color: #c1c71e;">我是测试员</h2>
	</center>
	<div class="vertical-horizontal-center form-container" id="formApp">
		<form onsubmit="return false;">
			<div class="form-group">
				<label class="item text-center">用户名</label><input v-model="user.username" class="item" type="text" placeholder="请输入用户名" >
			</div>
			<div class="form-group">
				<label class="item text-center">密码</label><input v-model="user.password" class="item" type="password" placeholder="请输入密码">
			</div>
			<div class="form-group">
				<label class="item text-center">生日</label><input v-model="user.birthday" class="item" type="date" placeholder="请输入生日">
			</div>
			<div class="form-group">
				<label class="item text-center">性别</label>
				<span class="item">
					<input name="sex" type="radio" value="male" v-model="user.sex">男
					<input name="sex" type="radio" value="female" v-model="user.sex">女
				</span>
			</div>
			<div class="form-group">
				<label class="item text-center">学历</label>
				<select class="item" v-model="user.xueli">
					<option value="0">本科</option>
					<option value="1">研究生</option>
					<option value="2">博士生</option>
					<option value="3">博士后</option>
				</select>
			</div>
			<div class="text-center">
				<button class="btn btn-submit" v-on:click="submitInfo">提交</button>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		window.onload = function(){
			console.log("I am load");
		}
	</script>
	<script>
		Vue.http.options.emulateJSON = true;
		var formApp = new Vue({
			el: '#formApp',
		  	data: {
		  		user:{
		  			"username": "",
		  			"password": "",
		  			"birthday": "",
		  			"sex": "",
		  			"xueli": ""
		  		}
		  	},
		  	methods:{
		  		submitInfo: function(){
		  			console.log(this.user);
		  			this.$http.post('/WsnWeb/login', this.user, {
		  		        'headers': {
		  		            'Content-Type': 'application/x-www-form-urlencoded'
		  		        }
		  		    }).then(function(res){
		  				console.log(res);
		  			}, function(err){
		  				console.log(res);
		  			});
		  		}
		  	}
		});
	</script>
</body>
</html>