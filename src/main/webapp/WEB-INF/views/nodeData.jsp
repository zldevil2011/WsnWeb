<%@ page contentType="text/html; charset=utf-8"%>
<jsp:include page="base.jsp" flush="true"/><!--动态包含-->  
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>节点数据</title>
<style type="text/css">
.body-container{
	width: 80%;
	margin: 0 auto;
}
.node-info{
	height: 150px;
	background-color: #f5f5f5;
}
.node-info .node-name{
    font-size: 28px;
    color: #555;
    line-height: 110px;
    text-indent: 10px;
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
</style>
</head>
<body>
<jsp:include page="headerMenu.jsp" flush="true"/><!--动态包含-->  
<div class="body-container" style="margin-top:100px;" id="node-data">
	<div class="node-info">
		<div class="node-name">
			望华楼 <span style="font-size:20px;color:#888;">监测点详细数据</span><span style="font-size:12px;color:#888;margin-right: 26px;display: inline-block;cursor:pointer;" onclick="window.location.href='/WsnWeb/node_list/'">更多>></span>
			<span style="float:right;font-size:12px;position:relative;top:9px;">更新时间：2017-12-01 12:00:00</span>
		</div>
		<div class="p-list">
			<span v-bind:class="{ active: parameter=='aqi'}" v-on:click="changeParameter('aqi')">AQI</span>
			<span v-bind:class="{ active: parameter=='pm25'}" v-on:click="changeParameter('pm25')">PM2.5</span>
			<span v-bind:class="{ active: parameter=='pm10'}" v-on:click="changeParameter('pm10')">PM10</span>
			<span v-bind:class="{ active: parameter=='so2'}" v-on:click="changeParameter('so2')">SO2</span>
			<span v-bind:class="{ active: parameter=='no2'}" v-on:click="changeParameter('no2')">NO2</span>
			<span v-bind:class="{ active: parameter=='co'}" v-on:click="changeParameter('co')">CO</span>
			<span v-bind:class="{ active: parameter=='o3'}" v-on:click="changeParameter('o3')">O3</span>
		</div>
	</div>
	<div class="graph" id="pm25" v-show="parameter == 'pm25'">
		<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
	</div>
	<div class="graph" id="pm10" v-show="parameter=='pm10'">
		<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
	</div>
	<div class="graph" id="so2" v-show="parameter=='so2'">
		<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
	</div>
	<div class="graph" id="no2" v-show="parameter=='no2'">
		<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
	</div>
	<div class="graph" id="o3" v-show="parameter=='o3'">
		<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
	</div>
	<div class="graph" id="co" v-show="parameter=='co'">
		<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
	</div>
	<div class="graph" id="aqi" v-show="parameter=='aqi'">
		<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
	</div>
	
</div>
<script>
	function paintToday(containerId, parameter, data){
		$('#'+containerId+' #today-data').highcharts({
	    	chart: {
	        	type: 'spline'
	    	},
	    	title: {
	        	text: '最近24小时' + parameter+'变化趋势'
	    	},
		    subtitle: {
		        text: ''
		    },
		    xAxis: {
		        type: 'datetime',
		        title: {
		            text: null
		        }
		    },
		    yAxis: {
		        title: {
		            text: '浓度(ug/m3)'
		        },
		        min: 0
		    },
		    tooltip: {
		        headerFormat: '<b>{series.name}</b><br>',
		        pointFormat: '{point.x:%e. %b}: {point.y:.2f} ug/m3'
		    },
		    plotOptions: {
		        spline: {
		            marker: {
		                enabled: true
		            }
		        }
		    },
		    series: [{
		        name: parameter+'浓度',
		        data: data
		    }]
		});
	}
	function paintMonth(containerId, parameter, data){ 
		$('#'+containerId+' #month-data').highcharts({
		    chart: {
		        type: 'spline'
		    },
		    title: {
		        text: '最近30天 '+parameter+'变化趋势'
		    },
		    subtitle: {
		        text: ''
		    },
		    xAxis: {
		        type: 'datetime',
		        title: {
		            text: null
		        }
		    },
		    yAxis: {
		        title: {
		            text: '浓度(ug/m3)'
		        },
		        min: 0
		    },
		    tooltip: {
		        headerFormat: '<b>{series.name}</b><br>',
		        pointFormat: '{point.x:%e. %b}: {point.y:.2f} ug/m3'
		    },
		    plotOptions: {
		        spline: {
		            marker: {
		                enabled: true
		            }
		        }
		    },
		    series: [{
		        name: parameter+'浓度',
		        data: data
		    }]
		});
	}
	paintToday("aqi", "AQI", createTodayData());
	function createTodayData(){
		var data = [];
		for(let i = 0; i < 24; ++i){
			var t = [Date.UTC(2017,10,1,i), parseFloat(Math.random()*100)];
			data.push(t);
		}
		return data;
	};
	function createMonthData(){
		var data = [];
		for(let i = 0; i < 31; ++i){
			var t = [Date.UTC(2017,10,i+1), parseFloat(Math.random()*100)];
			data.push(t);
		}
		return data;
	}
	paintToday("aqi", "AQI", createTodayData());
	paintMonth("aqi", "AQI", createMonthData());
	
	paintToday("pm25", "PM2.5", createTodayData());
	paintMonth("pm25", "PM2.5", createMonthData());
	
	paintToday("pm10", "PM10", createTodayData());
	paintMonth("pm10", "PM10", createMonthData());

	paintToday("so2", "SO2", createTodayData());
	paintMonth("so2", "SO2", createMonthData());

	paintToday("no2", "NO2", this.createTodayData());
	paintMonth("no2", "NO2", this.createMonthData());

	paintToday("o3", "O3", this.createTodayData());
	paintMonth("o3", "O3", this.createMonthData());

	paintToday("co", "CO", this.createTodayData());
	paintMonth("co", "CO", this.createMonthData());
	var nodeData = new Vue({
		el:"#node-data",
		data:{
			parameter:"pm25"
		},
		created:function(){
			this.init();
		},
		methods:{
			init:function(){
			},
			changeParameter:function(pm){
				this.parameter = pm;
			}
		}
	});
</script>
</body>
</html>