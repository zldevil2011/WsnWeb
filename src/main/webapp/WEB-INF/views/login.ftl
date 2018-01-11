<#include "base.ftl"/>
<title>个人用户登陆</title>
</head>
<body class="login-body">
	<div class="login-container" id="formApp">
		<form action="/" onsubmit="return false;">
			<h3>个人用户登陆</h3>
			<div class="input-group">
				<label>用户名</label><input v-model="user.username" type="text">
			</div>
			<div class="input-group">
				<label>密码</label><input v-model="user.password" type="password">
			</div>
			<div class="input-group" v-show="tip">
				<label style="width:100%;color:red;">用户名或密码错误</label>
			</div>
			<div>
				<button class="zl-btn btn btn-success btn-block" @click="checkLogin">登陆</button>
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
		  			this.$http.post('/WsnWeb/login', this.user, {
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