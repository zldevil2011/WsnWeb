<#include "adminBase.ftl"/>
<title>Warning Rule</title>
<style>
    #warningRule .el-row{
        margin-left: 5px !important;
        margin-right: 5px !important;;
    }
</style>
</head>
<body>
<div class="container" id="adminIndex">
    <#include "adminHeader.ftl"/>
    <el-button type="text" @click="dialogVisible = true">点击打开 Dialog</el-button>
    <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose">
        <span>这是一段信息</span>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>
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
    var warningRule = new Vue({
        el: "#adminIndex",
        data:{
            dialogVisible: false
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
                                nodeId: res.data[0].id
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
                        this.ruleList = res.data;
                        this.loadingDataList = false;
                    }
                }, function(err){
                    if(err.status != 200){
                        this.tip = true;
                    }
                });
            },
            handleClose(done) {
                this.$confirm('确认关闭？').then(
                        function(){
                            console.log('done');
                        }
                );
            }
        }
    })
</script>
<#include "adminFooter.ftl"/>