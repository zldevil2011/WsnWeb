<%@ page contentType="text/html; charset=utf-8"%>
<jsp:include page="base.jsp" flush="true"/><!--动态包含-->  
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>节点排名</title>
<style type="text/css">
.body-container{
	width: 80%;
	margin: 0 auto;
}
.p-list{
	display: flex;
	background-color:#ececec;
}
.p-list span{
	flex:1;
	text-align: center;
	border-right: 1px solid #cecdcd;
	height: 40px;
	line-height: 40px;
	border-bottom: #57b382 solid 4px;
	cursor:pointer;
}
.p-list span:hover{
	background-color: rgba(87, 179, 130, 0.3);
}
.p-list span.active{
	background-color: #57b382;
}
.table{
	text-align: center;
}
.rank-head{
    font-size: 28px;
    color: #555;
    line-height: 110px;
    text-indent: 10px;
    background-color: #f5f5f5;
}
</style>
</head>
<body>
<jsp:include page="headerMenu.jsp" flush="true"/><!--动态包含-->  
<div class="body-container" style="margin-top:100px;" id="node-rank">
	<div class="rank-head">
		排行 <span style="font-size:20px;color:#888;">各站点详细数据</span>
		<span style="float:right;font-size:12px;position:relative;top:9px;">更新时间：2017-12-01 12:00:00</span>
	</div>
	<div class="p-list">
		<span v-bind:class="{ active: parameter=='today'}" v-on:click="changeParameter('today')">今日排名</span>
		<span v-bind:class="{ active: parameter=='yesterday'}" v-on:click="changeParameter('yesterday')">昨日排名</span>
		<span v-bind:class="{ active: parameter=='week'}" v-on:click="changeParameter('week')">一周排名</span>
		<span v-bind:class="{ active: parameter=='month'}" v-on:click="changeParameter('month')">当月排名</span>
	</div>
	<div class="rank-data" style="padding-top:30px;">
		<table class="table"  v-show="parameter == 'today'">
			<thead>
				<tr><td>排名</td><td>质量</td><td>节点</td><td>AQI</td><td>PM2.5</td></tr>
			</thead>
			<tbody>
				<tr><td>1</td><td>优</td><td>望华楼</td><td>123</td><td>33</td></tr>
				<tr><td>2</td><td>优</td><td>望华楼</td><td>123</td><td>33</td></tr>
				<tr><td>3</td><td>优</td><td>望华楼</td><td>123</td><td>33</td></tr>
				<tr><td>4</td><td>优</td><td>望华楼</td><td>123</td><td>33</td></tr>
				<tr><td>5</td><td>优</td><td>望华楼</td><td>123</td><td>33</td></tr>
			</tbody>
		</table>
		<table class="table"  v-show="parameter == 'yesterday'">
			<thead>
				<tr><td>排名</td><td>质量</td><td>节点</td><td>AQI</td><td>PM2.5</td></tr>
			</thead>
			<tbody>
				<tr><td>1</td><td>优</td><td>池州港</td><td>123</td><td>33</td></tr>
				<tr><td>2</td><td>优</td><td>池州港</td><td>123</td><td>33</td></tr>
				<tr><td>3</td><td>优</td><td>池州港</td><td>123</td><td>33</td></tr>
				<tr><td>4</td><td>优</td><td>池州港</td><td>123</td><td>33</td></tr>
				<tr><td>5</td><td>优</td><td>池州港</td><td>123</td><td>33</td></tr>
			</tbody>
		</table>
		<table class="table"  v-show="parameter == 'week'">
			<thead>
				<tr><td>排名</td><td>质量</td><td>节点</td><td>AQI</td><td>PM2.5</td></tr>
			</thead>
			<tbody>
				<tr><td>1</td><td>优</td><td>太平鸟</td><td>123</td><td>33</td></tr>
				<tr><td>2</td><td>优</td><td>太平鸟</td><td>123</td><td>33</td></tr>
				<tr><td>3</td><td>优</td><td>太平鸟</td><td>123</td><td>33</td></tr>
				<tr><td>4</td><td>优</td><td>太平鸟</td><td>123</td><td>33</td></tr>
				<tr><td>5</td><td>优</td><td>太平鸟</td><td>123</td><td>33</td></tr>
			</tbody>
		</table>
		<table class="table"  v-show="parameter == 'month'">
			<thead>
				<tr><td>排名</td><td>质量</td><td>节点</td><td>AQI</td><td>PM2.5</td></tr>
			</thead>
			<tbody>
				<tr><td>1</td><td>优</td><td>青鸟</td><td>123</td><td>33</td></tr>
				<tr><td>2</td><td>优</td><td>青鸟</td><td>123</td><td>33</td></tr>
				<tr><td>3</td><td>优</td><td>青鸟</td><td>123</td><td>33</td></tr>
				<tr><td>4</td><td>优</td><td>青鸟</td><td>123</td><td>33</td></tr>
				<tr><td>5</td><td>优</td><td>青鸟</td><td>123</td><td>33</td></tr>
			</tbody>
		</table>
	</div>
</div>
<script>
var nodeRank = new Vue({
	el: "#node-rank",
	data: {
		"parameter":"today"
	},
	created:function(){
		
	},
	methods:{
		changeParameter:function(pm){
			this.parameter = pm;
		}
	}
});
</script>
</body>
</html>