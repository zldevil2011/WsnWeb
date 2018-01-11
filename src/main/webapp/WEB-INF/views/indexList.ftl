<#include "base.ftl"/>
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>首页</title>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container index-list-body-container" style="margin-top:100px;">
	<div class="head-info" id="devices-info">
		<div class="head-img">
			<img src="/WsnWeb/img/background.png" width="100%" height="200">
		</div>
		<div class="device-status">
			<div class="device-head">
				监测状态
			</div>
			<div class="device-info">
				设备总数目 {{ data.total }}，目前正常运行 {{data.normal}}，异常运行 {{data.trick}}，正常率 {{ data.normalRate | keepTwoNum}}
			</div>
		</div>
		<div class="device-list">
			<ul>
				<li>
					<span>名称</span><span class="time">安装日期</span><span>状态</span><span>最新数据日期</span><span>数据量</span>
				</li>
				<li v-for="node in nodeList">
					<span>{{node.nodeName}}</span>
					<span class="time">{{node.installTime}}</span>
					<span>{{node.status == 0 ? '在线':'离线'}}</span>
					<span>{{node.lastDataTime}}</span>
					<span>{{node.dataCount}}</span>
				</li>
				<li>
					<span style="text-align: right;padding-right: 20px;"><a href="/WsnWeb/node_list/"> 更多>></a></span>
				</li>
			</ul>
		</div>
	</div>
</div>
<script>
	var app = new Vue({
		el: "#devices-info",
		data: {
            data: {
                total: 12,
                normal: 10,
                trick: 2,
                normalRate: 10 / 12
            },
            nodeList: [
				{
                    nodeName:'齐山医药',
                    installTime:'2017-12-12 12:12:12',
                    status: '正常',
                    lastDataTime: '13',
					dataCount: '50'
				}
			]
        },
		created:function(){
		    this.init();
		},
		filters: {
		    keepTwoNum: function(value){
		        value = Number(value);
		        return value.toFixed(2);
			}
		},
		methods: {
		    init:function(){
		        // 异步获取设备列表数据
                this.$http.get('/WsnWeb/api/node_list').then(function(res){
                    console.log(res.data);
                    if(res.status != 200){
                        this.tip = true;
                    }else{
                        this.nodeList = res.data;
                        var total = 0;
                        var normal = 0;
                        var trick = 0;
                        total = this.nodeList.length;
                        for(var i = 0; i < total; ++i){
                            if(this.nodeList[i].status == 0){
                                normal += 1;
							}else{
                                trick += 1;
							}
						}
						this.data.normal = normal;
						this.data.total = total;
						this.data.trick = trick;
						this.data.normalRate = normal/total;
                    }
                }, function(err){
                    if(err.status != 200){
                        this.tip = true;
                    }
                });
            }
		}
	})
</script>
</body>
</html>