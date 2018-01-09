<%@ page contentType="text/html; charset=utf-8"%>
<jsp:include page="base.jsp" flush="true"/><!--动态包含-->  
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>首页</title>
<style type="text/css">
.body-container{
	width: 80%;
	margin: 0 auto;
}
.head-info{
	background-color: #f5f5f5;
}
.head-info .head-img{
}
</style>
</head>
<body>
<jsp:include page="headerMenu.jsp" flush="true"/><!--动态包含-->  
<div class="body-container" style="margin-top:100px;">
	<div class="node-info" id="node-data">
		<div class="head-img">
			<img src="/WsnWeb/img/background.png" width="100%" height="200">
		</div>
		
	</div>
</div>
<script>
	
</script>
</body>
</html>