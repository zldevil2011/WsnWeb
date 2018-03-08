<#include "base.ftl"/>
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>节点排名</title>
<style>
	.level-label{
        height: 12px; width: 30px;display: inline-block;border-radius: 2px;margin-left: 5px;
	}
	.level1_color{ background: #67cb01;  }
	.level2_color{ background: #f6e202;  }
	.level3_color{ background: #fb890f;  }
	.level4_color{ background: #e02d00;  }
	.level5_color{ background: #b414bb;  }
	.level6_color{ background: #6f0473;  }
</style>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container nodeRank-body-container" style="margin-top:100px;" id="node-rank">
	<div class="rank-head">
		排行 <span style="font-size:20px;color:#888;">各站点详细数据</span>
		<span style="float:right;font-size:12px;position:relative;top:9px;">更新时间：{{updateTime}}</span>
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
				<tr><th>排名</th><th>质量</th><th>节点</th><th>AQI</th><th>PM2.5</th><th>PM10</th><th>SO2</th></tr>
			</thead>
			<tbody>
				<tr v-for="node in todayNodes">
                    <td>{{node.rank}}</td>
                    <td>{{node.dataDesc}}
                        <span v-bind:class="'level-label level'+node.pollutionLevelNumber+'_color'"></span>
                    </td>
                    <td>{{node.nodeName}}</td>
                    <td>{{node.aqi | keepTwoNum}}</td>
                    <td>{{node.pm25 | keepTwoNum}}</td>
                    <td>{{node.pm10 | keepTwoNum}}</td>
                    <td>{{node.so2 | keepTwoNum}}</td>
				</tr>
				<tr><td colspan="7" align="center;"><img alt="" src="/WsnWeb/img/loading.gif" v-show="loadingToday"></td></tr>
			</tbody>
		</table>
		<table class="table" v-show="parameter == 'yesterday'">
			<thead>
            	<tr><th>排名</th><th>质量</th><th>节点</th><th>AQI</th><th>PM2.5</th><th>PM10</th><th>SO2</th></tr>
			</thead>
			<tbody>
				<tr v-for="node in yesterdayNodes">
                    <td>{{node.rank}}</td>
                    <td>{{node.dataDesc}}
                        <span v-bind:class="'level-label level'+node.pollutionLevelNumber+'_color'"></span>
                    </td>
                    <td>{{node.nodeName}}</td>
                    <td>{{node.aqi | keepTwoNum}}</td>
                    <td>{{node.pm25 | keepTwoNum}}</td>
                    <td>{{node.pm10 | keepTwoNum}}</td>
                    <td>{{node.so2 | keepTwoNum}}</td>
				</tr>
				<tr><td colspan="7" align="center;"><img alt="" src="/WsnWeb/img/loading.gif" v-show="loadingYesterday"></td></tr>
			</tbody>
		</table>
		<table class="table" v-show="parameter == 'week'">
			<thead>
            	<tr><th>排名</th><th>质量</th><th>节点</th><th>AQI</th><th>PM2.5</th><th>PM10</th><th>SO2</th></tr>
			</thead>
			<tbody>
				<tr v-for="node in weekNodes">
                    <td>{{node.rank}}</td>
                    <td>{{node.dataDesc}}
                        <span v-bind:class="'level-label level'+node.pollutionLevelNumber+'_color'"></span>
                    </td>
                    <td>{{node.nodeName}}</td>
                    <td>{{node.aqi | keepTwoNum}}</td>
                    <td>{{node.pm25 | keepTwoNum}}</td>
                    <td>{{node.pm10 | keepTwoNum}}</td>
                    <td>{{node.so2 | keepTwoNum}}</td>
				</tr>
				<tr><td colspan="7" align="center;"><img alt="" src="/WsnWeb/img/loading.gif" v-show="loadingWeek"></td></tr>
			</tbody>
		</table>
		<table class="table"  v-show="parameter == 'month'">
			<thead>
            	<tr><th>排名</th><th>质量</th><th>节点</th><th>AQI</th><th>PM2.5</th><th>PM10</th><th>SO2</th></tr>
			</thead>
			<tbody>
				<tr v-for="node in monthNodes">
                    <td>{{node.rank}}</td>
                    <td>{{node.dataDesc}}
                        <span v-bind:class="'level-label level'+node.pollutionLevelNumber+'_color'"></span>
                    </td>
                    <td>{{node.nodeName}}</td>
                    <td>{{node.aqi | keepTwoNum}}</td>
                    <td>{{node.pm25 | keepTwoNum}}</td>
                    <td>{{node.pm10 | keepTwoNum}}</td>
                    <td>{{node.so2 | keepTwoNum}}</td>
				</tr>
				<tr><td colspan="7" align="center;"><img alt="" src="/WsnWeb/img/loading.gif" v-show="loadingMonth"></td></tr>
			</tbody>
		</table>
		
	</div>
</div>
<script>
Date.prototype.MyFormat = function (fmt) { //author: meizz
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}
var nodeRank = new Vue({
	el: "#node-rank",
	data: {
		"parameter":"today",
		"todayNodes":"",
		"yesterdayNodes":"",
		"weekNodes":"",
		"monthNodes":"",
		"updateTime":"",
		"loadingToday":true,
		"loadingYesterday": true,
		"loadingWeek": true,
		"loadingMonth": true
	},
	created:function(){
		this.init();
	},
    filters: {
        keepTwoNum: function(value){
            if(value == null){
                return  "--"
            }else {
                value = Number(value);
                if(value <= 0){
                    return  "--";
				}else {
                    return value.toFixed(2);
                }
            }
        }
    },
	methods:{
		changeParameter:function(pm){
			this.parameter = pm;
		},
		init:function(){
			this.updateTime = new Date().MyFormat("yyyy-MM-dd hh:mm:ss");
			var $that = this;
			new Promise(function(resolve, reject){
				$that.$http.get('/WsnWeb/api/ranking_list?dataType=day').then(function(res){
	  		    	console.log(res.data);
	  				if(res.status != 200){
	  					$that.tip = true;
	  				}else{
	  					$that.loadingToday = false;
	  					$that.todayNodes = res.data;
	  					resolve();
	  				}
	  			}, function(err){
	  				if(err.status != 200){
	  					$that.tip = true;
	  				}
	  			});	
			}).then(function(resolve){
				$that.$http.get('/WsnWeb/api/ranking_list?dataType=yesterday').then(function(res){
	  		    	console.log(res.data);
	  				if(res.status != 200){
	  					$that.tip = true;
	  				}else{
	  					$that.loadingYesterday = false;
	  					$that.yesterdayNodes = res.data;
	  					resolve();
	  				}
	  			}, function(err){
	  				if(err.status != 200){
	  					$that.tip = true;
	  				}
	  			});	
			}).then(function(resolve){
				$that.$http.get('/WsnWeb/api/ranking_list?dataType=week').then(function(res){
	  		    	console.log(res.data);
	  				if(res.status != 200){
	  					$that.tip = true;
	  				}else{
	  					$that.loadingWeek = false;
	  					$that.weekNodes = res.data;
	  					resolve();
	  				}
	  			}, function(err){
	  				if(err.status != 200){
	  					$that.tip = true;
	  				}
	  			});	
			}).then(function(resolve){
				$that.$http.get('/WsnWeb/api/ranking_list?dataType=month').then(function(res){
	  		    	console.log(res.data);
	  				if(res.status != 200){
	  					$that.tip = true;
	  				}else{
	  					$that.loadingMonth = false;
	  					$that.monthNodes = res.data;
	  				}
	  			}, function(err){
	  				if(err.status != 200){
	  					$that.tip = true;
	  				}
	  			});	
			});
		}
	}
});
</script>
</body>
</html>