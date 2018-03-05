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
            各站点实时AQI指数（ 更新时间：{{ updateTime }} ）
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
                    <td v-if="data.pm25">{{data.pm25 | keepTwoNum}}</td><td v-else class="no-data">---</td>
                    <td v-if="data.aqi">{{data.aqi | keepTwoNum}}</td><td v-else class="no-data">---</td>

                    <td v-if="data.pm25">{{data.pm10 | keepTwoNum}}</td><td v-else class="no-data">---</td>
                    <td v-if="data.aqi">{{data.aqi | keepTwoNum}}</td><td v-else class="no-data">---</td>

                    <td v-if="data.so2">{{data.so2 | keepTwoNum}}</td><td v-else class="no-data">---</td>
                    <td v-if="data.aqi">{{data.aqi | keepTwoNum}}</td><td v-else class="no-data">---</td>

                    <td v-if="data.no2">{{data.no2 | keepTwoNum}}</td><td v-else class="no-data">---</td>
                    <td v-if="data.aqi">{{data.aqi | keepTwoNum}}</td><td v-else class="no-data">---</td>

                    <td v-if="data.co">{{data.co | keepTwoNum}}</td><td v-else class="no-data">---</td>
                    <td v-if="data.aqi">{{data.aqi | keepTwoNum}}</td><td v-else class="no-data">---</td>

                    <td v-if="data.o3">{{data.o3 | keepTwoNum}}</td><td v-else class="no-data">---</td>
                    <td v-if="data.aqi">{{data.aqi | keepTwoNum}}</td><td v-else class="no-data">---</td>

                    <td v-if="data.aqi">{{data.aqi | keepTwoNum}}</td><td v-else class="no-data">---</td>

                    <td>PM2.5</td>

                    <td v-if="data.level">{{data.level}}</td><td v-else class="no-data">-</td>

                    <td class="item-center">{{data.classification}}<span style="background-color:green; width: 20px; height: 12px; display: inline-block;margin-left: 10px;border-radius: 5px;"></span></td>
                </tr>
                <tr><td colspan="20" align="center;"><img alt="" src="/WsnWeb/img/loading.gif" v-show="loadingAqiInfo"></td></tr>
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
    Date.prototype.MyTimeFormat = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    };
    Vue.http.options.emulateJSON = true;
	var aqiInfoData = new Vue({
		el:"#aqiInfo-data",
		data:{
            aqiInfoDataList: '',
            loadingAqiInfo: true,
            updateTime: new Date(),
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
                this.updateTime = this.updateTime.MyTimeFormat("yyyy-MM-dd HH:mm:ss");
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
                        this.loadingAqiInfo = false;
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