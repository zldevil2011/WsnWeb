<#include "base.ftl"/>
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>AQI数据</title>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container dataWarning-body-container" style="margin-top:100px;">
	<div id="aqiInfo-data" class="aqiInfo-data">
        <div class="search-box" style="padding-bottom: 10px;text-align: center;">
            <p>观测站点异常与报警数据（ 更新时间：{{ updateTime }} ）</p>
            <form class="form-inline" onsubmit="return false;">
                <div class="form-group">
                    <label for="province">时间</label>
                    <input type="date" class="form-control" id="startTime" v-model="search_info.startTime">
                    <input type="date" class="form-control" id="endTime" v-model="search_info.endTime">
                </div>
                <div class="form-group">
                <select class="form-control select-100" v-model="search_info.nodeId">
                    <option v-for="node in nodeList" v-bind:value='node.id'>{{node.nodeName}}</option>
                </select>
				</div>
                <button type="submit" class="btn btn-success btn-100" style="margin-left: 10px;padding: 5px 12px;" @click="getInfoData">查找</button>
            </form>
        </div>
        <div class="table-container">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>#</th>
                    <th>地址</th>
                    <th>站点</th>
                    <th>类型</th>
                    <th>参数</th>
                    <th>时间</th>
                    <th>内容</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(data,index) in warningList">
                    <th scope="row">{{index+1}}</th>
                    <td>{{data.nodeAddress}}</td>
                    <td>{{data.nodeName}}</td>
                    <td>{{data.dataType}}</td>
                    <td>{{data.parameter}}</td>
                    <td>{{data.warningTime}}</td>
                    <td>{{data.content}}</td>
                </tr>
                <tr><td colspan="7" align="center;"><img alt="" src="/WsnWeb/img/loading.gif" v-show="loadingDataWarning"></td></tr>
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
            loadingDataWarning: true,
            warningList:[{
                "nodeAddress": '安徽池州',
                "nodeName": '站点一',
                "dataType": '异常',
                "parameter": 'SO2',
                "warningTime": '2017-12-12 12:12:12',
                "content": 'so2异常'
            }],
            nodeList:[],
            updateTime: new Date(),
			search_info: {
                nodeId: '',
                startTime: '',
                endTime: ''
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
                this.loadNodesList();
            },
            getInfoData: function(){
                this.loadingDataWarning = true;
                this.loadData();
			},
            loadNodesList:function(){
                this.$http.get('/WsnWeb/api/node_list/').then(function(res){
                    if(res.status != 200){
                        this.tip = true;
                    }else{
                        console.log(res.data);
                        if(res.data.length > 0){
                            console.log("yes123");
                            this.search_info ={
                                nodeId: res.data[0].id,
                                startTime: new Date().MyTimeFormat("yyyy-MM-dd"),
                                endTime: new Date(new Date().getTime() + 1000*60*60*24).MyTimeFormat("yyyy-MM-dd")
                            };
                        }
                        this.nodeList = res.data;
                        this.loadData();
                    }
                }, function(err){
                    if(err.status != 200){
                        this.tip = true;
                    }
                });
            },
			loadData: function(){
                this.$http.post('/WsnWeb/api/node_warning_list/', this.search_info ,{
                    'headers': {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function(res){
                    if(res.status != 200){
                        this.tip = true;
                    }else{
                        console.log(res.data);
                        this.warningList = res.data;
                        this.loadingDataWarning = false;
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