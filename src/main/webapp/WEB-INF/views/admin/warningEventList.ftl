<#include "adminBase.ftl"/>
<title>Device Warning Event List</title>
<style>
    .el-date-editor .el-range-separator{
        width: 6%;
    }
</style>
</head>
<body>
<div class="container device-list" id="warningEventList">
<#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <div>
            <div class="search-box" style="padding-bottom: 10px;text-align: center;">
                <p>观测站点异常与报警数据（ 更新时间：{{ updateTime }} ）</p>
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
                    <el-form-item>
                        <el-button type="primary" @click="getInfoData">查询</el-button>
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
                            prop="nodeAddress"
                            label="站点地址"
                            width="200">
                    </el-table-column>
                    <el-table-column
                            prop="nodeName"
                            label="站点"
                            width="150">
                    </el-table-column>
                    <el-table-column
                            prop="warningTime"
                            label="时间"
                            width="200">
                    </el-table-column>
                    <el-table-column
                            prop="dataType"
                            label="异常类型"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="parameter"
                            label="参数"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="content"
                            label="内容"
                            width="200">
                    </el-table-column>
                    <el-table-column label="操作"
                            width="200">
                        <template slot-scope="scope">
                            <el-button
                                    size="mini"
                                    @click="handleEdit(scope.$index, scope.row)">查看</el-button>
                            <el-button
                                    size="mini"
                                    type="danger"
                                    @click="handleDelete(scope.$index, scope.row)">发送通知</el-button>
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
    var warningEventList = new Vue({
        el: "#warningEventList",
        data: {
            warningActive: true,
            loadingDataList: true,
            dataList:[],
            nodeList:[],
            updateTime: new Date().MyTimeFormat("yyyy-MM-dd HH:mm:ss"),
            search_info: {
                timeRange: [new Date(new Date().getTime() - 1000*60*60*24).MyTimeFormat("yyyy-MM-dd"), new Date().MyTimeFormat("yyyy-MM-dd")],
                nodeId: '0',
                startTime: new Date(new Date().getTime() - 1000*60*60*24).MyTimeFormat("yyyy-MM-dd"),
                endTime: new Date().MyTimeFormat("yyyy-MM-dd"),
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
        methods:{
            init: function () {
                this.loadNodesList();
            },
            getInfoData: function(){
                this.loadingDataList = true;
                this.loadData();
            },
            loadNodesList:function(){
                this.$http.get('/WsnWeb/api/node_list/').then(function(res){
                    if(res.status != 200){
                        this.tip = true;
                    }else{
                        if(res.data.length > 0){
                            this.search_info ={
                                timeRange: [new Date(new Date().getTime() - 1000*60*60*24).MyTimeFormat("yyyy-MM-dd"), new Date().MyTimeFormat("yyyy-MM-dd")],
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
                        this.dataList = res.data;
                        this.loadingDataList = false;
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
<#include "adminFooter.ftl"/>