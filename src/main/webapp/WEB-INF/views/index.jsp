<%@ page contentType="text/html; charset=utf-8"%>
<jsp:include page="base.jsp" flush="true"/><!--动态包含-->  
<title>首页</title>
<style type="text/css">
	html, body{
		height: 100%;
	}
	.body-container{
		position:relative;
	}
	.header{
		position: absolute;
	    top: 0px;
	    left: 0px;
	    width: 100%;
	    height: 70px;
	    background-color: #1e9e69;
	    border-bottom: #57b382 solid 4px;
	    z-index: 20;
	}
	.header ul li{
		display:inline-block;
		line-height: 70px;
		padding: 0 20px;
		font-family: "Microsoft YaHei",Arial, Verdana, Helvetica, sans-serif;
    	color: white;
    	cursor: pointer;
	}
	.header ul li:hover{
		background-color: rgba(108, 218, 173, 0.5);
	}
	.left-map{
		background-color:#262734;
		width: 100%;
		height: 100%;
	}
	.right-slide{
		position: absolute;
		top: 0;
		right: 0;
		background-color:#1f202b;
		border-left: 1px solid #111111;
		width: 310px;
		min-height: 100%;
	}
</style>
</head>
<body>
	<div class="header">
		<ul>
			<li>首页</li>
			<li>实时监测</li>
			<li>排行榜</li>
			<li>最新新闻</li>
		</ul>
	</div>
	<div class="body-container">
		<div class="left-map">
			右侧地图栏
		</div>
		<div class="right-slide">
			右侧数据栏
		</div>
	</div>
	<script>
	</script>
</body>
</html>