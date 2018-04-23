<#include "adminBase.ftl"/>
<title>Information List</title>
<style>
    #informationList .table-container{
        margin-left:10px !important;
        margin-right:10px !important;
    }
</style>
</head>
<body>
<div class="container" id="informationList">
<#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <div>
            <div class="table-container">
                <el-table
                        :data="dataList"
                        style="width: 100%"
                        v-loading.body="loadingDataList">
                    <el-table-column
                            type="index"
                            width="50">
                    </el-table-column>
                    <el-table-column
                            prop="title"
                            label="标题">
                    </el-table-column>
                    <el-table-column
                            prop="readCount"
                            label="阅读量"
                            width="150">
                    </el-table-column>
                    <el-table-column
                            prop="author"
                            label="作者"
                            width="200">
                    </el-table-column>
                    <el-table-column
                            prop="isDeleted"
                            label="上架"
                            width="100">
                        <template slot-scope="scope">
                            <span v-if="scope.row.isDeleted==0">是</span><span v-else>否</span>
                        </template>
                    </el-table-column>
                    </el-table-column>
                    <el-table-column label="操作"
                            width="200">
                        <template slot-scope="scope">
                            <el-button
                                    size="mini"
                                    @click="handleView(scope.$index, scope.row)">查看</el-button>
                            <el-button
                                    size="mini"
                                    type="danger"
                                    @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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
    var informationList = new Vue({
        el: "#informationList",
        data: {
            documentActive: true,
            loadingDataList: true,
            dataList:[]
        },
        created:function(){
            this.init();
        },
        methods:{
            init: function () {
                this.loadData();
            },
            loadData: function(){
                this.$http.get('/WsnWeb/api/information_list').then(function(res){
                    console.log(res.data);
                    if(res.status != 200){
                        this.$message.error('拉取数据失败');
                    }else{
                        this.$message({
                            message: '更新数据成功',
                            type: 'success'
                        });
                        this.dataList = res.data;
                        this.loadingDataList = false;
                    }
                }, function(err){
                    this.$message.error('拉取数据失败' + String(err));
                });
            }
        }
    })
</script>
<#include "adminFooter.ftl"/>