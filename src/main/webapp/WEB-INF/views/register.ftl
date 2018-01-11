<#include "base.ftl"/>
<title>个人用户注册</title>
</head>
<body class="register-body">
	<div class="register-container" id="formApp">
		<form action="/" onsubmit="return false;">
			<h3>个人用户注册</h3>
			<div class="input-group">
				<label>用户名</label><input v-model="user.username" type="text">
			</div>
			<div class="input-group">
				<label>密码</label><input v-model="user.password" type="password">
			</div>
			<div class="input-group">
				<label>电话</label><input v-model="user.telephone" type="text">
			</div>
			<div class="input-group">
				<label>地址</label><input v-model="user.address" type="text">
			</div>
			<div>
				<button class="zl-btn btn btn-success btn-block" @click="checkLogin">注册</button>
			</div>
		</form>
	</div>
	<script>
		Vue.http.options.emulateJSON = true;
		var formApp = new Vue({
			el: '#formApp',
		  	data: {
		  		user:{
		  			"username": "",
		  			"password": ""
		  		},
		  		tip: false
		  	},
		  	methods:{
		  		checkLogin: function(){
		  			console.log(this.user);
		  			this.$http.post('/WsnWeb/register', this.user, {
		  		        'headers': {
		  		            'Content-Type': 'application/x-www-form-urlencoded'
		  		        }
		  		    }).then(function(res){
		  		    	console.log(res);
		  				if(res.status != 200){
		  					this.tip = true;
		  				}
		  			}, function(err){
		  				if(err.status != 200){
		  					this.tip = true;
		  				}
		  			});
		  		}
		  	}
		});
	</script>
</body>
</html>