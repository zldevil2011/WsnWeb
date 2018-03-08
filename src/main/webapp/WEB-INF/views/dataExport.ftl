<#include "base.ftl"/>
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>数据下载</title>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container dataExport-body-container" style="margin-top:100px;">
    <div id="dataExport" class="dataExport">
        <div class="search-box" style="padding-bottom: 10px;text-align: center;">
            <p>观测站点数据下载（ 更新时间：{{ updateTime }} ）</p>
            <form class="form-inline" onsubmit="return false;">
                <div class="form-group">
                    <label for="province">时间</label>
                    <input type="date" class="form-control" id="startTime" v-model="search_info.startTime">&nbsp;至
                    <input type="date" class="form-control" id="endTime" v-model="search_info.endTime">
                </div>
                <div class="form-group">
                    <select class="form-control select-100" v-model="search_info.nodeId">
                        <option value='0'>所有站点</option>
                        <option v-for="node in nodeList" v-bind:value='node.id'>{{node.nodeName}}</option>
                    </select>
                </div>
                <div class="form-group">
                    <select class="form-control select-100" v-model="search_info.requestType">
                        <option v-for="data in dataTypeList" v-bind:value='data.value'>{{data.name}}</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-success btn-100" style="margin-left: 10px;padding: 5px 12px;" @click="loadData">查找</button>
                <button type="submit" class="btn btn-warning btn-100" style="margin-left: 10px;padding: 5px 12px;" @click="dataExport">导出数据</button>
            </form>
        </div>
        <div class="table-container">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>#</th>
                    <th>站点</th>
                    <th>观测时间</th>
                    <th>Pm2.5</th>
                    <th>Pm10</th>
                    <th>So2</th>
                    <th>No2</th>
                    <th>Co</th>
                    <th>O3</th>
                    <th>AQI</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(data,index) in dataList">
                    <td scope="row">{{index+1}}</td>
                    <td data-id="data.nodeId">{{data.nodeName}}</td>
                    <td>{{data.updateTime}}</td>
                    <td>{{data.pm25 | keepTwoNum}}</td>
                    <td>{{data.pm10 | keepTwoNum}}</td>
                    <td>{{data.so2 | keepTwoNum}}</td>
                    <td>{{data.no2 | keepTwoNum}}</td>
                    <td>{{data.co | keepTwoNum}}</td>
                    <td>{{data.o3 | keepTwoNum}}</td>
                    <td>{{data.aqi | keepTwoNum}}</td>
                </tr>
                <tr><td colspan="10" align="center;"><img alt="" src="/WsnWeb/img/loading.gif" v-show="loadingDataList"></td></tr>
                </tbody>
            </table>
        </div>
        <div class="footer-info" style="text-align: center;margin-bottom: 2px;margin-top: 10px;">
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
    var dataExport = new Vue({
        el:"#dataExport",
        data:{
            loadingDataList: true,
            dataTypeList:[{
                value: 'all',
                name: '所有数据'
            },{
                value: 'hour',
                name: '小时平均'
            },{
                value: 'day',
                name: '日平均'
            }],
            dataList:[{
                "nodeName": '站点一',
                "updateTime": '2017-12-12 12:00:00',
                "pm25": '12',
                "pm10": '12',
                "so2": '12',
                "no2": '12',
                "co": '12',
                "o3": '12'
            }],
            nodeList:[],
            updateTime: new Date(),
            search_info: {
                nodeId: 0,
                startTime: '2018-03-01',//new Date(new Date().getTime() - 1000*60*60*24).MyTimeFormat("yyyy-MM-dd"),
                endTime: '2018-03-02', //new Date().MyTimeFormat("yyyy-MM-dd")
                requestType: 'hour'
            }
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
                    return value.toFixed(2);
                }
            }
        },
        methods: {
            init: function () {
                this.updateTime = this.updateTime.MyTimeFormat("yyyy-MM-dd HH:mm:ss");
                // 初始化完成之后加载站点列表
                this.loadNodesList();
            },
            getInfoData: function(){
                this.loadingDataList = true;
                this.loadData();
            },
            loadNodesList:function(){
                var that = this;
                this.$http.get('/WsnWeb/api/node_list/').then(function(res){
                    if(res.status != 200){
                        this.tip = true;
                    }else{
                        that.nodeList = res.data;
                        // 站点列表加载完成之后加载全部站点的昨天到今天零点的所有数据
                        this.loadData();
                    }
                }, function(err){
                    if(err.status != 200){
                        this.tip = true;
                    }
                });
            },
            loadData: function(){
                this.dataList = [];
                this.loadingDataList = true;
                this.$http.post('/WsnWeb/api/all_nodes_historical_data/', this.search_info ,{
                    'headers': {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function(res){
                    if(res.status != 200){

                    }else{
                        this.loadingDataList = false;
                        var tmpDataList = [];
                        res.data.forEach(function(item){
                            tmpDataList = tmpDataList.concat(item);
                        });
                        this.dataList = tmpDataList;
                    }
                }, function(err){
                    if(err.status != 200){
                    }
                });
            },
            dataExport:function(){
                console.log(this.dataList);
                window.location.href= "/WsnWeb/api/excelExport?startTime=" + this.search_info.startTime + "&endTime=" + this.search_info.endTime + "&nodeId=" + this.search_info.nodeId + "&requestType=" + this.search_info.requestType;
            }
        }
    })
</script>
</body>
</html>