<#include "adminBase.ftl"/>
<title>Admin Index</title>
<style></style>
</head>
<body>
<div class="container" id="adminIndex">
    <#include "adminHeader.ftl"/>
    <div class="row clearfix">
        <div class="col-md-12">
            <div class="jumbotron">
                <h2>
                    今日数据
                </h2>
                <p>设备运行：18台正常， 0台异常</p>
                <p>报警：1条</p>
                <p>访问：1000</p>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="row">
                <div class="col-md-4">
                    <div class="thumbnail">
                        <img alt="300x200" src="http://ibootstrap-file.b0.upaiyun.com/lorempixel.com/600/200/people/default.jpg" />
                        <div class="caption">
                            <h3>
                                PM2.5
                            </h3>
                            <p>
                                最高值，最低值，平均
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="thumbnail">
                        <img alt="300x200" src="http://ibootstrap-file.b0.upaiyun.com/lorempixel.com/600/200/people/default.jpg" />
                        <div class="caption">
                            <h3>
                                PM10
                            </h3>
                            <p>
                                最高值，最低值，平均
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="thumbnail">
                        <img alt="300x200" src="http://ibootstrap-file.b0.upaiyun.com/lorempixel.com/600/200/people/default.jpg" />
                        <div class="caption">
                            <h3>
                                AQI
                            </h3>
                            <p>
                                最高值，最低值，平均
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var adminIndex = new Vue({
        el: "#adminIndex",
        data:{
            indexActive: true,
        }
    })
</script>
<#include "adminFooter.ftl"/>