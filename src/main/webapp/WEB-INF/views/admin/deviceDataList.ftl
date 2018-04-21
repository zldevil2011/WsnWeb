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
</style>
</head>
<body>
<div class="container device-list" id="warningEventList">
<#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <el-table
                    :data="tableData5"
                    style="width: 100%">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="PM2.5">
                                <span>{{ props.row.pm25 }}</span>
                            </el-form-item>
                            <el-form-item label="PM10">
                                <span>{{ props.row.pm10 }}</span>
                            </el-form-item>
                            <el-form-item label="SO2">
                                <span>{{ props.row.so2 }}</span>
                            </el-form-item>
                            <el-form-item label="NO2">
                                <span>{{ props.row.no2 }}</span>
                            </el-form-item>
                            <el-form-item label="CO">
                                <span>{{ props.row.co }}</span>
                            </el-form-item>
                            <el-form-item label="O3">
                                <span>{{ props.row.o3 }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column
                        label="ID"
                        prop="id">
                </el-table-column>
                <el-table-column
                        label="设备"
                        prop="name">
                </el-table-column>
                <el-table-column
                        label="时间"
                        prop="date">
                </el-table-column>
            </el-table>
        </div>
    </div>
</div>
<script>
    var deviceList = new Vue({
        el: "#warningEventList",
        data: {
            tableData5: [{
                id: '1',
                name: '赛维机械',
                date: '2018-03-03 12:12:12',
                pm25: 22,
                pm10: 56.7,
                so2: 0.1,
                no2: 1.3,
                co: 3.4,
                o3: 5.5
            }, {
                id: '2',
                name: '赛维机械',
                date: '2018-03-03 22:10:12',
                pm25: 22,
                pm10: 56.7,
                so2: 0.1,
                no2: 1.3,
                co: 3.4,
                o3: 5.5
            }, {
                id: '3',
                name: '赛维机械',
                date: '2018-03-04 10:12:12',
                pm25: 22,
                pm10: 56.7,
                so2: 0.1,
                no2: 1.3,
                co: 3.4,
                o3: 5.5
            }, {
                id: '4',
                name: '赛维机械',
                date: '2018-03-03 12:44:13',
                pm25: 22,
                pm10: 56.7,
                so2: 0.1,
                no2: 1.3,
                co: 3.4,
                o3: 5.5
            }],
            dataActive: true,
        },
        created:function(){
            this.init();
        },
        methods:{
            init:function(){

            }
        }
    })
</script>
<#include "adminFooter.ftl"/>