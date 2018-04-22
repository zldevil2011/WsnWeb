<#include "adminBase.ftl"/>
<title>Device Data List</title>
<style>
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
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
<div class="container device-list" id="warningEventList">
<#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <el-table
                    :data="deviceDataList"
                    v-loading.body="loading"
                    style="width: 100%">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="PM2.5">
                                <span>{{ props.row.pm25 ? props.row.pm25 + 'ug/m3' : '无数据' }}</span>
                            </el-form-item>
                            <el-form-item label="PM10">
                                <span>{{ props.row.pm10 ? props.row.pm10 + 'ug/m3' : '无数据'  }}</span>
                            </el-form-item>
                            <el-form-item label="SO2">
                                <span>{{ props.row.so2 ? props.row.so2 + 'ug/m3' : '无数据'  }}</span>
                            </el-form-item>
                            <el-form-item label="NO2">
                                <span>{{ props.row.no2 ? props.row.no2 + 'ug/m3' : '无数据'  }}</span>
                            </el-form-item>
                            <el-form-item label="CO">
                                <span>{{ props.row.co ? props.row.co + 'mg/m3' : '无数据'  }}</span>
                            </el-form-item>
                            <el-form-item label="O3">
                                <span>{{ props.row.o3 ? props.row.o3 + 'ug/m3' : '无数据'  }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column
                        label="ID"
                        prop="nodeId">
                </el-table-column>
                <el-table-column
                        label="设备"
                        prop="nodeName">
                </el-table-column>
                <el-table-column
                        label="时间"
                        prop="updateTime">
                </el-table-column>
                <el-table-column
                        label="环境质量">
                    <template slot-scope="scope">
                        <span v-bind:class="'level-label level'+scope.row.pollutionLevelNumber+'_color'"></span>{{scope.row.classification}}
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</div>
<script>
    var deviceList = new Vue({
        el: "#warningEventList",
        data: {
            loading: true,
            deviceDataList: [],
            dataActive: true,
        },
        created:function(){
            this.init();
        },
        methods:{
            init:function(){
                this.$http.post('/WsnWeb/api/node_list_aqi/', this.search_info ,{
                    'headers': {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function(res){
                    if(res.status != 200){
                        this.$message.error('拉取数据失败！');
                    }else{
                        this.$message({
                            message: '数据已刷新',
                            type: 'success'
                        });
                        this.deviceDataList = res.data;
                    }
                    this.loading = false;
                }, function(err){
                    if(err.status != 200){
                        this.$message.error('拉取数据失败！');
                    }
                });
            }
        }
    })
</script>
<#include "adminFooter.ftl"/>