<#include "adminBase.ftl"/>
<title>Warning Rule</title>
<style>
    #warningRule .el-row{
        margin-left: 5px !important;
        margin-right: 5px !important;;
    }
    .el-dialog__wrapper .el-dialog{
        width: 40%;
    }
    .el-dialog__wrapper .el-dialog__body{
        padding-right: 60px;
    }
    .el-dialog__body .el-select{
        width: 100%;
    }
</style>
</head>
<body>
<div class="container" id="warningRule">
<#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <div class="row">
            <div class="search-box" style="padding-bottom: 10px;text-align: center;">
                <p>报警规则（ 更新时间：{{ updateTime }} ）</p>
                <el-form :inline="true" :model="search_info" class="demo-form-inline">
                    <el-form-item label="站点">
                        <el-select placeholder="目标站点" v-model="search_info.nodeId">
                            <el-option label="所有站点" value="0" selected>所有站点</el-option>
                            <el-option v-for="(node,index) in nodeList" v-bind:value='node.id' :label="node.nodeName">{{node.nodeName}}</el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="getInfoData" size="medium">查询</el-button>
                        <el-button type="primary" @click="dialogVisible = true" size="medium">新建</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <el-row :gutter="20" class="card-list">
            <el-col :span="6" v-for="(rule,index) in ruleList" key={{index}}>
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>规则{{index+1}}</span>
                        <el-button style="float: right; padding: 3px 0" type="text">编辑</el-button>
                    </div>
                    <div>
                        <p>站点:<span>{{ rule.nodeName }}</span></p>
                        <p>参数:<span>{{ rule.parameter }}</span></p>
                        <p>类型:<span>{{ rule.ruleType | filterRuleType}}</span></p>
                        <p>阈值:<span>{{ rule.ruleValue }}</span></p>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
    <el-dialog title="新建规则" :visible.sync="dialogVisible">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="站点" prop="nodeId">
                <el-select v-model="ruleForm.nodeId" placeholder="请选择站点">
                    <el-option label="所有站点" value="0" selected>所有站点</el-option>
                    <el-option v-for="(node,index) in nodeList" v-bind:value='node.id' :label="node.nodeName">{{node.nodeName}}</el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="参数" prop="parameter">
                <el-select v-model="ruleForm.parameter" placeholder="请选择参数">
                    <el-option label="PM2.5" value="pm25"></el-option>
                    <el-option label="PM10" value="pm10"></el-option>
                    <el-option label="SO2" value="so2"></el-option>
                    <el-option label="NO2" value="no2"></el-option>
                    <el-option label="CO" value="co"></el-option>
                    <el-option label="O3" value="o3"></el-option>
                    <el-option label="采集" value="collect"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="类型" prop="ruleType">
                <el-select v-model="ruleForm.ruleType" placeholder="请选择类型">
                    <el-option label="阈值" value="1"></el-option>
                    <el-option label="增长率" value="2"></el-option>
                    <el-option label="采集异常" value="0"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="阈值" prop="ruleValue" v-if="ruleForm.ruleType > 0">
                <el-input v-model="ruleForm.ruleValue"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
        </div>
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
        el: "#warningRule",
        data:{
            warningActive: true,
            loadingDataList: true,
            ruleList:[],
            nodeList:[],
            updateTime: new Date().MyTimeFormat("yyyy-MM-dd HH:mm:ss"),
            search_info: {
                nodeId: '0'
            },
            dialogVisible: false, // 弹窗是否隐藏
            ruleForm: {
                nodeId: '',
                parameter: '',
                ruleType: '',
                ruleValue: ''
            },
            rules: {
                device: [
                    { required: true, message: '请选择站点', trigger: 'change' }
                ],
                parameter: [
                    { required: true, message: '请选择参数', trigger: 'change' }
                ],
                ruleType: [
                    { required: true, message: '请选择类型', trigger: 'change' }
                ],
                ruleValue: [
                    { required: true, message: '请输入规则阈值', trigger: 'blur' },
                ]
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
            },
            filterRuleType: function(value){
                if(value == 0){
                    return  "采集异常";
                }else if(value == 1){
                    return  "超过阈值";
                }else if(value == 2){
                    return  "增长过快"
                }else{
                    return  "未知类型"
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
                this.$http.get('/WsnWeb/api/warningRule/warningRuleList/').then(function(res){
                    console.log(res.data);
                    if(res.status != 200){
                        this.$message.error('拉取数据失败');
                    }else{
                        this.$message({
                            message: '更新数据成功',
                            type: 'success'
                        });
                        this.ruleList = res.data;
                    }
                }, function(err){
                    this.$message.error('拉取数据失败' + err);
                });
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log(this.$refs[formName].model);

                        this.$http.post('/WsnWeb/api/warningRule/warningRuleAdd/', this.$refs[formName].model ,{
                            'headers': {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            }
                        }).then(function(res){
                            if(res.status != 200){
                                this.$message.error('错了哦，这是一条错误消息' + err);
                                console.log(err);
                            }else{
                                this.$message({
                                    message: '恭喜成功',
                                    type: 'success'
                                });
                                window.location.reload();
                            }
                        }, function(err){
                            this.$message.error('错了哦，这是一条错误消息' + err);
                            console.log(err);
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            }
        }
    })
</script>
<#include "adminFooter.ftl"/>