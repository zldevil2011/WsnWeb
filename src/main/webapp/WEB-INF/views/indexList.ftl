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
					<span>{{node.nodeName}}</span><span class="time">{{node.installTime}}</span><span>{{node.province}}</span><span>{{node.installTime}}</span><span>{{node.pm10}}</span>
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
                    province: '正常',
					pm25: '13',
					pm10: '50',
					so2: '--',
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
                this.$http.get('/WsnWeb/api/index_node_rank').then(function(res){
                    console.log(res.data);
                    if(res.status != 200){
                        this.tip = true;
                    }else{
                        this.nodeList = res.data;
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