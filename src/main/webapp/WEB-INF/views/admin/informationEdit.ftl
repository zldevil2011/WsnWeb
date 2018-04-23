<#include "adminBase.ftl"/>
<title>Information Edit</title>
<style>
    #informationList .table-container{
        margin-left:10px !important;
        margin-right:10px !important;
    }
    .el-switch{
        height: 40px;
    }
</style>
</head>
<body>
<div class="container" id="createInformation">
<#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="标题" prop="title">
                <el-input v-model="ruleForm.title"></el-input>
            </el-form-item>
            <el-form-item label="上架" prop="upload">
                <el-switch v-model="ruleForm.upload"></el-switch>
            </el-form-item>
            <el-form-item label="内容" prop="content">
                <el-input type="textarea" v-model="ruleForm.content" rows="18"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
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
    var createInformation = new Vue({
        el: "#createInformation",
        data: {
            documentActive: true,
            ruleForm: {
                title: '',
                upload: '',
                content: ''
            },
            rules: {
                title: [
                    { required: true, message: '请输入标题', trigger: 'blur' }
                ],
                upload: [
                    { required: true, message: '请选择是否上架', trigger: 'blur' }
                ],
                content: [
                    { required: true, message: '请填写内容', trigger: 'blur' }
                ]
            }
        },
        created:function(){
            this.init();
        },
        methods:{
            init: function () {
            },
            submitForm(formName) {
                console.log(this.$refs);
                console.log(this.$refs[formName]);
                this.$http.post('/WsnWeb/api/informationAdd/', this.$refs[formName].model ,{
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
                        window.location.href='/WsnWeb/adminWsn/informationList';
                    }
                }, function(err){
                    this.$message.error('错了哦，这是一条错误消息' + err);
                    console.log(err);
                });
//                this.$refs[formName].validate((valid) => {
//                    if (valid) {
//                        alert('submit!');
//                        console.log($refs[formName].model);
//                    } else {
//                        console.log('error submit!!');
//                        return false;
//                    }
//                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    })
</script>
<#include "adminFooter.ftl"/>