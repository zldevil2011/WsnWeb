<#include "adminBase.ftl"/>
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>Device Data Download</title>
<style>
    .el-date-editor .el-range-separator{
        width: 6%;
    }
</style>
</head>
<body>
<div class="container device-list" id="deviceDataExport">
<#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <div>
            <div class="search-box" style="padding-bottom: 10px;text-align: center;">
                <p>观测站点数据下载（ 更新时间：{{ updateTime }} ）</p>
                <el-form :inline="true" :model="search_info" class="demo-form-inline">
                    <el-form-item label="时间">
                        <el-date-picker
                                v-model="search_info.timeRange"
                                type="daterange"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                value-format="yyyy-MM-dd">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="站点">
                        <el-select placeholder="目标站点" v-model="search_info.nodeId">
                            <el-option label="所有站点" value="0" selected>所有站点</el-option>
                            <el-option v-for="node in nodeList" v-bind:value='node.id' :label="node.nodeName">{{node.nodeName}}</el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="时间尺度">
                        <el-select placeholder="选择时间尺度" v-model="search_info.requestType">
                            <el-option v-for="data in dataTypeList" v-bind:value='data.value' :label="data.lable">{{data.lable}}</el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="loadData">查询</el-button>
                        <el-button type="primary" @click="dataExport">导出</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="table-container">
                <el-table
                        :data="dataList"
                        style="width: 100%"
                        v-loading.body="loadingDataList"
                        max-height="500">
                    <el-table-column
                            type="index"
                            width="50">
                    </el-table-column>
                    <el-table-column
                            prop="nodeName"
                            label="站点"
                            width="200">
                    </el-table-column>
                    <el-table-column
                            prop="updateTime"
                            label="观测时间"
                            width="200">
                    </el-table-column>
                    <el-table-column
                            label="PM2.5"
                            width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.pm25 | keepTwoNum}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="PM10"
                            width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.pm10 | keepTwoNum}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="SO2"
                            width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.so2 | keepTwoNum}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="no2"
                            label="NO2"
                            width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.pm25 | keepTwoNum}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="CO"
                            width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.co | keepTwoNum}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="O3"
                            width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.o3 | keepTwoNum}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="AQI"
                            width="100">
                        <template slot-scope="scope">
                            <span>{{scope.row.aqi | keepTwoNum}}</span>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</div>
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
    var deviceDataExport = new Vue({
        el:"#deviceDataExport",
        data:{
            dataActive: true,
            loadingDataList: true,
            dataTypeList:[{
                value: 'all',
                lable: '所有数据'
            },{
                value: 'hour',
                lable: '小时平均'
            },{
                value: 'day',
                lable: '日平均'
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
                timeRange: [new Date(new Date().getTime() - 1000*60*60*24).MyTimeFormat("yyyy-MM-dd"), new Date().MyTimeFormat("yyyy-MM-dd")],
                nodeId: '0',
                startTime: new Date(new Date().getTime() - 1000*60*60*24).MyTimeFormat("yyyy-MM-dd"),
                endTime: new Date().MyTimeFormat("yyyy-MM-dd"),
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
                if(this.search_info.timeRange){
                    this.search_info.startTime = this.search_info.timeRange[0];
                    this.search_info.endTime = this.search_info.timeRange[1];
                }
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
                        this.dataList = tmpDataList.slice(0, 500);
                    }
                }, function(err){
                    if(err.status != 200){
                    }
                });
            },
            dataExport:function(){
                if(this.search_info.timeRange){
                    this.search_info.startTime = this.search_info.timeRange[0];
                    this.search_info.endTime = this.search_info.timeRange[1];
                }
                window.location.href= "/WsnWeb/api/excelExport?startTime=" + this.search_info.startTime + "&endTime=" + this.search_info.endTime + "&nodeId=" + this.search_info.nodeId + "&requestType=" + this.search_info.requestType;
            }
        }
    })
</script>
<#include "adminFooter.ftl"/>