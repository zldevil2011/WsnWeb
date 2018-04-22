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
                        <span>规则1</span>
                        <el-button style="float: right; padding: 3px 0" type="text">编辑</el-button>
                    </div>
                    <div>
                        <p>站点:<span>{{ rule.station }}</span></p>
                        <p>参数:<span>{{ rule.parameter }}</span></p>
                        <p>类型:<span>{{ rule.ruleType }}</span></p>
                        <p>阈值:<span>{{ rule.ruleValue }}</span></p>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>规则1</span>
                        <el-button style="float: right; padding: 3px 0" type="text">编辑</el-button>
                    </div>
                    <div>
                        <p>站点:<span>测试站点</span></p>
                        <p>参数:<span>PM2.5</span></p>
                        <p>类型:<span>阈值</span></p>
                        <p>阈值:<span>50</span></p>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>规则1</span>
                        <el-button style="float: right; padding: 3px 0" type="text">编辑</el-button>
                    </div>
                    <div>
                        <p>站点:<span>测试站点</span></p>
                        <p>参数:<span>PM2.5</span></p>
                        <p>类型:<span>阈值</span></p>
                        <p>阈值:<span>50</span></p>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>规则1</span>
                        <el-button style="float: right; padding: 3px 0" type="text">编辑</el-button>
                    </div>
                    <div>
                        <p>站点:<span>测试站点</span></p>
                        <p>参数:<span>PM2.5</span></p>
                        <p>类型:<span>阈值</span></p>
                        <p>阈值:<span>50</span></p>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>规则1</span>
                        <el-button style="float: right; padding: 3px 0" type="text">编辑</el-button>
                    </div>
                    <div>
                        <p>站点:<span>测试站点</span></p>
                        <p>参数:<span>PM2.5</span></p>
                        <p>类型:<span>阈值</span></p>
                        <p>阈值:<span>50</span></p>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
    <el-dialog title="收货地址" :visible.sync="dialogVisible">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="站点" prop="device">
                <el-select v-model="ruleForm.device" placeholder="请选择站点">
                    <el-option label="站点一" value="1"></el-option>
                    <el-option label="站点二" value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="参数" prop="parameter">
                <el-select v-model="ruleForm.parameter" placeholder="请选择参数">
                    <el-option label="参数一" value="1"></el-option>
                    <el-option label="参数二" value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="类型" prop="ruleType">
                <el-select v-model="ruleForm.ruleType" placeholder="请选择类型">
                    <el-option label="类型一" value="1"></el-option>
                    <el-option label="类型二" value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="阈值" prop="ruleValue">
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
                device: '',
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
            submitForm(formName) {
                console.log(this.$refs);
                console.log(this.$refs[formName]);

                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
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