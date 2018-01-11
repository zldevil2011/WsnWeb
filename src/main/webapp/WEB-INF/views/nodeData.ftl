<#include "base.ftl"/>
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>节点数据</title>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container nodeData-body-container" style="margin-top:100px;">
	<div class="node-info" id="node-data">
		<div class="node-name">
			{{node_info.nodeName}}<span style="font-size:20px;color:#888;">监测点详细数据</span><span style="font-size:12px;color:#888;margin-right: 26px;display: inline-block;cursor:pointer;" onclick="window.location.href='/WsnWeb/node_list/'">更多>></span>
			<span style="float:right;font-size:12px;position:relative;top:9px;">更新时间：{{latestData.updateTime}}</span>
		</div>
		<div class="main-data">
			<div class="item data-circle">
				<div class="data">
					<span style="position: relative;top: 57px;font-size: 18px;">AQI指数<br>{{latestData.aqi}}</span>
				</div>
			</div>
			<div class="item data-circle">
				<div class="data">
					<span style="position: relative;top: 57px;font-size: 18px;">PM2.5<br>{{latestData.pm25}}</span>
				</div>
			</div>
			<div class="item data-circle">
				<div class="data">
					<span style="position: relative;top: 57px;font-size: 18px;">PM10<br>{{latestData.pm10}}</span>
				</div>
			</div>
			<div class="right-tip item">
				<div style="margin:0 auto;width: 235px;height: 11px;background: url(/WsnWeb/img/data_color.png) -6px -4px no-repeat;"></div>
                <div style="font-size: 12px;opacity: 0.5;padding-top: 5px;">
                    0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;50&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;100&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;150&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;200&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;300&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;500
                </div>
                <div style="font-size: 12px;opacity: 0.5;padding-top: 5px;">
                    <span class="level2_color"><b>{{latestData.level}}</b></span>
                </div>
                <div style="color:#7db364;text-align: left;">健康影响:</div>
                <div style="text-align: left;">{{latestData.conclusion}}</div><br>
                <div style="color:#7db364;text-align: left;">温馨提示:</div>
                <div style="text-align: left;">{{latestData.advice}}</div>
			</div>
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
	<div>
		<div class="graph" id="pm25">
			<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
			<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		</div>
		<div class="graph" id="pm10">
			<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
			<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		</div>
		<div class="graph" id="so2">
			<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
			<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		</div>
		<div class="graph" id="no2">
			<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
			<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		</div>
		<div class="graph" id="o3">
			<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
			<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		</div>
		<div class="graph" id="co">
			<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
			<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;"></div>
		</div>
		<div class="graph" id="aqi">
			<div class="today-data" id="today-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;">
				<img alt="" src="/WsnWeb/img/loading.gif" style="position:relative;top:110px;left:50%;" v-show="loadingToday">
			</div>
			<div class="month-data" id="month-data" style="height:310px;background-color:#f7f7f7;border:1px solid e5e5e5;margin-top:40px;">
				<img alt="" src="/WsnWeb/img/loading.gif" style="position:relative;top:120px;left:50%;" v-show="loadingMonth">
			</div>
		</div>
	</div>
	<div id="node-historical-data" class="node-historical-data">
        <div class="search-box">
            <form class="form-inline">
                <div class="form-group">
                    <label for="province">时间</label>
                    <input type="date" class="form-control" id="startTime">
                    <input type="date" class="form-control" id="endTime">
                </div>
                <div class="form-group">
                <select class="form-control select-100">
                    <option>所有数据</option>
                    <option>小时数据</option>
                    <option>日均数据</option>
                </select>
				</div>
                <button type="submit" class="btn btn-success btn-100" style="margin-left: 10px;padding: 5px 12px;">查找</button>
            </form>
        </div>
        <div class="table-container">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>#</th>
                    <th>状态</th>
                    <th>监测时间</th>
                    <th>PM2.5(ug/m³)</th>
                    <th>PM10(ug/m³)</th>
                    <th>SO2(ug/m³)</th>
                    <th>NO2(ug/m³)</th>
                    <th>CO(ug/m³)</th>
                    <th>O3(ug/m³)</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(data,index) in nodeList">
                    <th scope="row">{{index+1}}</th>
                    <td>{{data.dataStatus == 0 ? '正常' : data.dataStatus == 1 ? '异常' : '超标'}}</td>
                    <td>{{data.updateTime}}</td>
                    <td>{{data.pm25}}</td>
                    <td>{{data.pm10}}</td>
                    <td>{{data.so2}}</td>
                    <td>{{data.no2}}</td>
                    <td>{{data.co}}</td>
                    <td>{{data.o3}}</td>
                </tr>
                </tbody>
            </table>
			<div class="footer-info">

			</div>
        </div>
	</div>
</div>
<script>
	var nodeId = '${node_id}';
	console.log(nodeId);
	/* function createTodayData(){
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
	paintMonth("co", "CO", this.createMonthData()); */
	var nodeData = new Vue({
		el:"#node-data",
		data:{
			parameter:"aqi",
			node_info: "",
			latestData: "",
			loadingToday: true,
			loadingMonth: true
		},
		created:function(){
			this.init();
		},
		methods:{
			init:function(){
				//获取该节点的信息
				this.node_info = {
					"nodeName": "无数据"
				};
				this.latestData = {
					"updateTime": "无数据",	
					"aqi": "无数据",
					"pm25": "无数据",
					"pm10": "无数据",
					"level": "无数据",
					"conclusion": "无数据",
					"advice": "无数据",
				};
				$("#aqi").css("display", "block");
				this.$http.get('/WsnWeb/api/node_info/' + nodeId).then(function(res){
	  				if(res.status != 200){
	  					this.tip = true;
	  				}else{
	  					let data = res.data;
	  					this.node_info = data;
	  				}
	  			}, function(err){
	  				if(err.status != 200){
	  					this.tip = true;
	  				}
	  			});
				// 获取该节点的最新的数据信息
				this.$http.get('/WsnWeb/api/node_latest_data/' + nodeId).then(function(res){
					console.log(res);
	  				if(res.status != 200){
	  					this.tip = true;
	  				}else{
	  					let data = res.data;
	  					console.log(data == "");
	  					if(data == ""){
	  					}else{
	  						this.latestData = data;
	  					}
	  				}
	  			}, function(err){
	  				if(err.status != 200){
	  					this.tip = true;
	  				}
	  			});
				// 获取该数据点当天的数据
				this.$http.get('/WsnWeb/api/data_list/' + nodeId + "?dataType=day").then(function(res){
	  				if(res.status != 200){
	  					this.tip = true;
	  				}else{
	  					this.loadingToday = false;
	  					let data = res.data;
	  					console.log(data);
	  					// 获取8条数据，分别对应七项采集的数据，最后一项是时间安排（今日的小时数据）
	  					var pList = ["pm25", "pm10", "so2", "no2", "o3", "co", "aqi"];
	  					var len = data.length;
	  					// 首先构造一个UTC的时间数组，对应今日的小时机制
	  					let timeList = [];
	  					let today = new Date();
	  					let year = today.getFullYear();
	  					let month = today.getMonth();
	  					let day = today.getDate();
	  					for(let i = 0; i < data[7].length; ++i){
	  						let t = Date.UTC(year,month,day, data[7][i]);
	  						timeList.push(t);
	  					}
	  					for(let i = 0; i < len - 1; ++i){
	  						let pName = pList[i];
	  						let dLen = data[i].length;
	  						let pData = [];
	  						for(let j = 0; j < dLen; ++j){
	  							let t = [timeList[j], data[i][j]];
	  							pData.push(t);
	  						}
	  						paintToday(pName, pName.toUpperCase(), pData);
	  					}
	  				}
	  			}, function(err){
	  				if(err.status != 200){
	  					this.tip = true;
	  				}
	  			});
				// 获取该数据点最近一个月的日平均数据
				this.$http.get('/WsnWeb/api/data_list/' + nodeId + "?dataType=month").then(function(res){
	  				if(res.status != 200){
	  					this.tip = true;
	  				}else{
	  					this.loadingMonth = false;
	  					let data = res.data;
	  					console.log(data);
	  					// 获取8条数据，分别对应七项采集的数据，最后一项是时间安排（最近的30天日期）
	  					var pList = ["pm25", "pm10", "so2", "no2", "o3", "co", "aqi"];
	  					var len = data.length;
	  					// 首先构造一个UTC的时间数组，对应三十天的日期
	  					let timeList = [];
	  					let today = new Date();
	  					let year = today.getFullYear();
	  					let month = today.getMonth();
	  					for(let i = 0; i < data[7].length; ++i){
	  						let t = Date.UTC(year,data[8][i],data[7][i]);
	  						timeList.push(t);
	  					}
	  					for(let i = 0; i < len - 2; ++i){
	  						let pName = pList[i];
	  						let dLen = data[i].length;
	  						let pData = [];
	  						for(let j = 0; j < dLen; ++j){
	  							let t = [timeList[j], data[i][j]];
	  							pData.push(t);
	  						}
	  						paintMonth(pName, pName.toUpperCase(), pData);
	  					}
	  				}
	  			}, function(err){
	  				if(err.status != 200){
	  					this.tip = true;
	  				}
	  			});
			},
			changeParameter:function(pm){
				this.parameter = pm;
				$("#"+pm).siblings().css("display", "none");
				$("#"+pm).css("display", "block");
			}
		}
	});
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
</script>
</body>
</html>