<#include "base.ftl"/>
<title>新闻列表</title>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container newsList-body-container" id="news-list" style="margin-top: 100px;">
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