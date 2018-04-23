<#include "base.ftl"/>
<title>新闻列表</title>
<!-- 引入样式 -->
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<!-- 引入组件库 -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<style>
	html, body{
		height: 100%;
	}
</style>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container" id="newsDetail" style="margin-top: 100px;">
	<div v-if="news.newsAddress.length > 0"> <!-- 外网 -->
        <iframe v-bind:src="'http://www.pm25.com'+news.newsAddress" height="100%" width="100%"></iframe>
	</div>
	<div v-else>
		<div>
			I am test
		</div>
	</div>
</div>
<script>
    var news_id = '${news_id}';
	var newsDetail = new Vue({
		el: "#newsDetail",
		data: {
		    news: ''
		},
		created:function(){
			this.init();
		},
		methods:{
			init:function(){
				this.$http.get('/WsnWeb/api/newsDetails/' + news_id).then(function(res){
	  		    	console.log(res.data);
	  				if(res.status != 200){
                        this.$message.error('拉取数据失败！');
	  				}else{
                        this.$message({
                            message: '数据已刷新',
                            type: 'success'
                        });
                        this.news = res.data;
	  				}
	  			}, function(err){
                    this.$message.error('拉取数据失败！');
	  			});
			}
		}
	})
</script>
</body>
</html>