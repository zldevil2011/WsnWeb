<%@ page contentType="text/html; charset=utf-8"%>
<jsp:include page="base.jsp" flush="true"/><!--动态包含-->  
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>首页</title>
<style type="text/css">
	.body-container{
		width: 80%;
		margin: 0 auto;
	}
	.head-info{
		background-color: #f5f5f5;
	}
	.device-status{
		padding-left: 10px;
	}
	.device-status .device-head{
		font-size: 24px;
		color:black;
	}
	.device-status .device-info{
		padding: 10px 0;
		font-size: 16px;
	}
	.device-list{
		padding: 20px 10px;
	}
	.device-list ul{
		-webkit-margin-before: 0;
		-webkit-padding-start: 0;
	}
	.device-list ul li{
		list-style-type: none;
		display: flex;
		justify-content: space-between;
		-webkit-margin-before:0;
	}
	.device-list ul li span.time{
		flex: 2;
	}
	.device-list ul li span{
		flex: 1;
		text-align: center;
		padding: 10px 0;
	}
</style>
</head>
<body>
<jsp:include page="headerMenu.jsp" flush="true"/><!--动态包含-->  
<div class="body-container" style="margin-top:100px;">
	<div class="head-info">
		<div class="head-img">
			<img src="/WsnWeb/img/background.png" width="100%" height="200">
		</div>
		<div class="device-status" id="devices-info">
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
					<span>名称</span><span class="time">采集日期</span><span>状态</span><span>PM2.5(ug/m3)</span><span>PM10(ug/m3)</span><span>NO2(ug/m3)</span><span>CO(ug/m3)</span><span>SO2(ug/m3)</span><span>O3(ug/m3)</span>
				</li>
				<li>
					<span>齐山医药</span><span class="time">2017-12-12 12:00:00</span><span>异常</span><span>13</span><span>53</span><span>--</span><span>--</span><span>--</span><span>--</span>
				</li>
				<li>
					<span style="text-align: right;padding-right: 20px;"><a href=""> 更多>></a></span>
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
            }
        },
		filters: {
		    keepTwoNum: function(value){
		        value = Number(value);
		        return value.toFixed(2);
			}
		}
	})
</script>
</body>
</html>