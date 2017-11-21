<%@ page contentType="text/html; charset=utf-8"%>
<jsp:include page="base.jsp" flush="true"/><!--动态包含-->  
<title>新闻列表</title>
<style type="text/css">
.body-container{
	width: 80%;
	margin: 0 auto;
}
.right-news-list{
	float:right;
	width: 250px;
}
.right-news-list .right-head{
	border-bottom: 1px solid gray;
	font-size: 18px;
    color: #888;
}
.right-news-list .news-list ul{
	-webkit-padding-start:20px;
}
.right-news-list .news-list ul li{
	list-style: unset;
	margin-top: 10px;
}
.left-news-list{
	margin-right: 260px;
}
.left-news-list .left-head{
	border-bottom: 1px solid gray;
	font-size: 18px;
    color: #888;
}
.left-news-list .news-list .news{
	height: 170px;
	border-bottom: 1px dotted gray;
	padding: 10px;
	box-sizing: border-box;
	cursor:pointer;
}
.left-news-list .news-list .news:hover{
	background-color:rgba(236, 236, 230, 0.2);
}

.left-news-list .news-list .news .right-news-info .news-title{
	font-size: 16px;
	padding: 5px 0;
}
.left-news-list .news-list .news .right-news-info .news-desc{
	font-size: 12px;
	padding: 10px 0;
}
</style>
</head>
<body>
<jsp:include page="headerMenu.jsp" flush="true"/><!--动态包含-->  
<div class="body-container" id="node-list" style="margin-top: 100px;">
	<div class="right-news-list">
		<div class="right-head">热门新闻</div>
		<div class="news-list">
			<ul>
				<li>郑州前5个月才27个好天 市领导分包各区治理雾霾</li>
				<li>郑州前5个月才27个好天 市领导分包各区治理雾霾</li>
				<li>郑州前5个月才27个好天 市领导分包各区治理雾霾</li>
				<li style="text-align:right;">更多>></li>
			</ul>
		</div>
		
	</div>
	<div class="left-news-list">
		<div class="left-head">最新新闻</div>
		<div class="news-list">
			<div class="news">
				<div class="left-img" style="float:left;width:150px;border-radius:5px;">
					<img src="http://www.pm25.com/data/attached/image/20160317/20160317132832_13122.jpg" width = "150" height="150" style="border-radius:15px;">
				</div>
				<div class="right-news-info"  style="margin-left:160px;">
					<div class="news-title">
						绿萝、吊兰、芦荟摆再多都没用！吸甲醛、PM2.5，效果最好居然是它！
					</div>
					<div class="news-desc">
						很多人喜欢买一些植物放在家里，办公室里。可是，你知道什么植物净化空气的效率最高吗？绿萝？吊兰？芦荟？No No No！答案竟然是……卖个关子，请往下看。
					</div>
				</div>
			</div>
			<div class="news">
				<div class="left-img" style="float:left;width:150px;border-radius:5px;">
					<img src="http://www.pm25.com/data/attached/image/20160317/20160317132832_13122.jpg" width = "150" height="150" style="border-radius:15px;">
				</div>
				<div class="right-news-info"  style="margin-left:160px;">
					<div class="news-title">
						绿萝、吊兰、芦荟摆再多都没用！吸甲醛、PM2.5，效果最好居然是它！
					</div>
					<div class="news-desc">
						很多人喜欢买一些植物放在家里，办公室里。可是，你知道什么植物净化空气的效率最高吗？绿萝？吊兰？芦荟？No No No！答案竟然是……卖个关子，请往下看。
					</div>
				</div>
			</div>
			<div class="news">
				<div class="left-img" style="float:left;width:150px;border-radius:5px;">
					<img src="http://www.pm25.com/data/attached/image/20160317/20160317132832_13122.jpg" width = "150" height="150" style="border-radius:15px;">
				</div>
				<div class="right-news-info"  style="margin-left:160px;">
					<div class="news-title">
						绿萝、吊兰、芦荟摆再多都没用！吸甲醛、PM2.5，效果最好居然是它！
					</div>
					<div class="news-desc">
						很多人喜欢买一些植物放在家里，办公室里。可是，你知道什么植物净化空气的效率最高吗？绿萝？吊兰？芦荟？No No No！答案竟然是……卖个关子，请往下看。
					</div>
				</div>
			</div>
			<div class="news">
				<div class="left-img" style="float:left;width:150px;border-radius:5px;">
					<img src="http://www.pm25.com/data/attached/image/20160317/20160317132832_13122.jpg" width = "150" height="150" style="border-radius:15px;">
				</div>
				<div class="right-news-info"  style="margin-left:160px;">
					<div class="news-title">
						绿萝、吊兰、芦荟摆再多都没用！吸甲醛、PM2.5，效果最好居然是它！
					</div>
					<div class="news-desc">
						很多人喜欢买一些植物放在家里，办公室里。可是，你知道什么植物净化空气的效率最高吗？绿萝？吊兰？芦荟？No No No！答案竟然是……卖个关子，请往下看。
					</div>
				</div>
			</div>
		</div>
	</div>
	
</div>
<script>
	
</script>
</body>
</html>