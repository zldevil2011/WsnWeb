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
	height: 160px;
	border-bottom: 1px dotted gray;
	padding: 25px 10px;
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
<div class="body-container" id="news-list" style="margin-top: 100px;">
	<div class="right-news-list">
		<div class="right-head">热门新闻</div>
		<div class="news-list">
			<ul>
				<li v-for="news in hotNews">{{news.title}}</li>
				<li style="text-align:right;">更多>></li>
			</ul>
		</div>
		
	</div>
	<div class="left-news-list">
		<div class="left-head">最新新闻</div>
		<div class="news-list">
			<div class="news" v-for="news in latestNews">
				<div class="left-img" style="float:left;width:150px;border-radius:5px;">
					<img v-bind:src="news.headImg" width = "150" height="110" style="border-radius:15px;">
				</div>
				<div class="right-news-info"  style="margin-left:160px;">
					<div class="news-title">
						{{news.title}} <span style="color:gray;font-size:12px;">-- {{news.author}}</span>
					</div>
					<div class="news-desc">
						{{news.description}}
					</div>
				</div>
			</div>
		</div>
	</div>
	
</div>
<script>
	var newsList = new Vue({
		el: "#news-list",
		data: {
			hotNews:'',
			latestNews:''
		},
		created:function(){
			this.init();
		},
		methods:{
			init:function(){
				this.$http.get('/WsnWeb/api/information_list').then(function(res){
	  		    	console.log(res.data);
	  				if(res.status != 200){
	  					this.tip = true;
	  				}else{
	  					this.hotNews = res.data;
	  					this.latestNews = res.data;
	  				}
	  			}, function(err){
	  				if(err.status != 200){
	  					this.tip = true;
	  				}
	  			});
			}
		}
	})
</script>
</body>
</html>