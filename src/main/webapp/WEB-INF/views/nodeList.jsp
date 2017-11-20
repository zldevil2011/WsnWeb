<%@ page contentType="text/html; charset=utf-8"%>
<jsp:include page="base.jsp" flush="true"/><!--动态包含-->  
<title>节点列表</title>
<style type="text/css">
.body-container{
	width: 80%;
	margin: 0 auto;
}
.city-head{
	margin-top:100px;
	height:50px;
	line-height: 50px;
	font-size:24px;
	background-color: #57b382;
	text-indent:10px;
}
.search-head{
	height:42px;
	line-height: 42px;
	background-color: #efefef;
}
.search-head span{
	display: inline-block;
	width: 120px;
	text-align: center;
	cursor: pointer;
}
.search-head span.active{
	background-color: #dfdfdf;
}
.left-head{
	float: left;
    display: inline;
    display: inline;
    width: 120px;
    font-size: 24px;
    margin-top: 8px;
    color: #c8c8c8;
}
.right-content{
	float: left;
    display: inline;
    padding: 6px 0px 15px 0px;
    margin-top: 8px;
    margin-bottom: 0;
}
.right-content a{
	padding: 4px 9px;
    margin-top: 5px;
    margin-right: 1px;
    cursor:pointer;
}
.list-data .item{
	display: block;
	margin-bottom:0;
	overflow:hidden;
}
</style>
</head>
<body>
<jsp:include page="headerMenu.jsp" flush="true"/><!--动态包含-->  
<div class="body-container">
	<div class="city-head">
		选择节点
	</div>
	<hr>
	<div>
		<p><span style="font-weight: bold;">关注节点</span>：池州港 望华楼</p>
	</div>
	<hr>
	<div class="search-head">
		<span class="active">按地区</span><span>按节点</span>
	</div>
	<div class="list-data">
		<dl class="item">
			<dt class="left-head">安徽池州</dt>
			<dd class="right-content">
				<a>望华楼</a>
				<a>池州港</a>
				<a>齐山医药</a>
				<a>赛威机械</a>
				<a>创业园</a>
				<a>太平鸟</a>
			</dd>
		</dl>
		<dl class="item">
			<dt class="left-head">A</dt>
			<dd class="right-content">
				<a>望华楼</a>
			</dd>
		</dl>
		<dl class="item">
			<dt class="left-head">B</dt>
			<dd class="right-content">
				<a>池州港</a>
			</dd>
		</dl>
		<dl class="item">
			<dt class="left-head">C</dt>
			<dd class="right-content">
				<a>齐山医药</a>
			</dd>
		</dl>
		<dl class="item">
			<dt class="left-head">D</dt>
			<dd class="right-content">
				<a>赛威机械</a>
			</dd>
		</dl>
		<dl class="item">
			<dt class="left-head">E</dt>
			<dd class="right-content">
				<a>创业园</a>
			</dd>
		</dl>
	</div>
</div>
<script>
		
</script>
</body>
</html>