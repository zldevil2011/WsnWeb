<#include "adminBase.ftl"/>
<title>Device List</title>
<style>
</style>
</head>
<body>
<div class="container device-list" id="deviceList">
    <#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                    <tr><th>编号</th><th>名称</th><th>经度</th><th>纬度</th><th>地址</th></tr>
                </thead>
                <tbody>
                    <tr v-for="(device,index) in deviceList">
                        <td>{{index + 1}}</td><td>{{device.nodeName}}</td><td>{{device.latitude}}</td><td>{{device.longitude}}</td><td>{{device.address}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    var deviceList = new Vue({
        el: "#deviceList",
        data: {
            deviceList:[],
            deviceActive: true,
        },
        created:function(){
            this.init();
        },
        methods:{
            init:function(){
                this.$http.get('/WsnWeb/api/node_list').then(function(res){
                    console.log(res.data);
                    if(res.status != 200){
                        this.$message.error('拉取数据失败！');
                    }else{
                        this.$message({
                            message: '数据已刷新',
                            type: 'success'
                        });
                        this.deviceList = res.data;
                    }
                }, function(err){
                    this.$message.error('拉取数据失败！');
                });
            }
        }
    })
</script>
<#include "adminFooter.ftl"/>