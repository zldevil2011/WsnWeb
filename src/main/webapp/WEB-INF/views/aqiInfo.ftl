<#include "base.ftl"/>
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>AQI数据</title>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container aqiInfo-body-container" style="margin-top:100px;">
	<div id="aqiInfo-data" class="aqiInfo-data">
        <div class="search-box" style="padding-bottom: 10px;">
            各站点实时AQI指数
            <#--<form class="form-inline" onsubmit="return false;">-->
                <#--<div class="form-group">-->
                    <#--<label for="province">时间</label>-->
                    <#--<input type="date" class="form-control" id="startTime" v-model="search_info.startTime">-->
                    <#--<input type="date" class="form-control" id="endTime" v-model="search_info.endTime">-->
                <#--</div>-->
                <#--<div class="form-group">-->
                <#--<select class="form-control select-100" v-model="search_info.requestType">-->
                    <#--<option value="all">所有数据</option>-->
                    <#--<option value="hour">小时数据</option>-->
                    <#--<option value="day">日均数据</option>-->
                <#--</select>-->
				<#--</div>-->
                <#--<button type="submit" class="btn btn-success btn-100" style="margin-left: 10px;padding: 5px 12px;" @click="getInfoData">查找</button>-->
            <#--</form>-->
        </div>
        <div class="table-container">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>#</th>
                    <th>状态</th>
                    <th>地址</th>
                    <th>站点</th>
                    <th>PM2.5</th><th>指数</th>
                    <th>PM10</th><th>指数</th>
                    <th>SO2</th><th>指数</th>
                    <th>NO2</th><th>指数</th>
                    <th>CO</th><th>指数</th>
                    <th>O3</th><th>指数</th>
                    <th>AQI指数</th>
                    <th>首要污染物</th>
                    <th>空气等级</th>
                    <th>质量</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(data,index) in aqiInfoDataList">
                    <th scope="row">{{index+1}}</th>
                    <td>{{data.dataStatus == 0 ? '正常' : data.dataStatus == 1 ? '异常' : '超标'}}</td>
                    <td>{{data.nodeAddress}}</td>
                    <td>{{data.nodeName}}</td>
                    <td>{{data.pm25 | keepTwoNum}}</td><td>{{data.aqi | keepTwoNum}}</td>
                    <td>{{data.pm10 | keepTwoNum}}</td><td>{{data.aqi | keepTwoNum}}</td>
                    <td>{{data.so2 | keepTwoNum}}</td><td>{{data.aqi | keepTwoNum}}</td>
                    <td>{{data.no2 | keepTwoNum}}</td><td>{{data.aqi | keepTwoNum}}</td>
                    <td>{{data.co | keepTwoNum}}</td><td>{{data.aqi | keepTwoNum}}</td>
                    <td>{{data.o3 | keepTwoNum}}</td><td>{{data.aqi | keepTwoNum}}</td>
                    <td>{{data.aqi | keepTwoNum}}</td>
                    <td>PM2.5</td>
                    <td>{{data.level}}</td>
                    <td class="item-center">{{data.classification}}<span style="background-color:green; width: 20px; height: 12px; display: inline-block;margin-left: 10px;border-radius: 5px;"></span></td>
                </tr>
                </tbody>
            </table>
			<div class="footer-info">

			</div>
        </div>
	</div>
</div>
<script>

</script>
<script>
    Vue.http.options.emulateJSON = true;
	var aqiInfoData = new Vue({
		el:"#aqiInfo-data",
		data:{
            aqiInfoDataList: '',
			search_info: {
                requestType: 'all',
                startTime: '2018-01-11',
                endTime: '2018-01-12'
			}
		},
        created:function(){
            this.init();
        },
        filters: {
            keepTwoNum: function(value){
                value = Number(value);
                return value.toFixed(2);
            }
        },
        methods: {
            init: function () {
                this.loadData();
            },
            getInfoData: function(){
                this.loadData();
			},
			loadData: function(){
                this.$http.post('/WsnWeb/api/node_list_aqi/', this.search_info ,{
                    'headers': {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function(res){
                    if(res.status != 200){
                        this.tip = true;
                    }else{
                        this.aqiInfoDataList = res.data;
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