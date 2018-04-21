<#include "adminBase.ftl"/>
<div class="container device-list" id="warningEventList">
<#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column
                        prop="date"
                        label="日期"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="deviceName"
                        label="设备"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="warningContent"
                        label="警报数据">
                </el-table-column>
            </el-table>
        </div>
    </div>
</div>
<script>
    var deviceList = new Vue({
        el: "#warningEventList",
        data: {
            tableData: [{
                date: '2016-05-02',
                deviceName: '赛维机械',
                warningContent: '采集异常'
            },{
                date: '2016-05-02',
                deviceName: '赛维机械',
                warningContent: '采集异常'
            }, {
                date: '2016-05-02',
                deviceName: '赛维机械',
                warningContent: '采集异常'
            },{
                date: '2016-05-02',
                deviceName: '赛维机械',
                warningContent: '采集异常'
            }],
            warningActive: true,
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