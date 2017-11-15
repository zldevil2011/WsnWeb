<%@ page contentType="text/html; charset=utf-8"%>
<jsp:include page="base.jsp" flush="true"/><!--动态包含-->  
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=WZVN4DbW3hfB9MSgMmwVQBHU8ZV8jRxW"></script>
<script async src="http://c.cnzz.com/core.php"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
<!-- 
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=2E92C953574c3fa5f86de9c14501c0ea"></script>
 -->
<title>首页</title>
<style type="text/css">
	html, body{
		height: 100%;
	}
	.body-container{
		position:relative;
		margin-top: 70px;
	}
	.header{
		position: absolute;
	    top: 0px;
	    left: 0px;
	    width: 100%;
	    height: 70px;
	    background-color: #1e9e69;
	    border-bottom: #57b382 solid 4px;
	    z-index: 20;
	}
	.header ul li{
		display:inline-block;
		line-height: 70px;
		padding: 0 20px;
		font-family: "Microsoft YaHei",Arial, Verdana, Helvetica, sans-serif;
    	color: white;
    	cursor: pointer;
	}
	.header ul li:hover{
		background-color: rgba(108, 218, 173, 0.5);
	}
	.left-map{
		background-color:#262734;
		width: 100%;
		height: 100%;
	}
	.right-slide{
		padding: 20px 10px;
		position: absolute;
		top: 0;
		right: 0;
		background-color:#1f202b;
		border-left: 1px solid #111111;
		width: 310px;
		min-height: 100%;
	}
	.search-input input{
		width: 100%;
		background: #303741;
	    padding: 15px;
	    border: none;
	    height: 22px;
	    display: inline;
	    text-indent: 15px;
	    color: #fff;
	    outline: none;
	}
	.node-lists{
		margin-top:20px;
		color: #c4ced8;
		margin-bottom: 20px;
		border-bottom: #252731 solid 3px;
	}
	.node-lists ul li{
		display: inline-block;
	    box-sizing: border-box;
	    width: 49%;
	    overflow: hidden;
	    padding: 10px;
	    background-color: #303741;
	    text-align: center;
	}
	.current-node{
		color: #c4ced8;
		margin-bottom: 20px;
		border-bottom: #252731 solid 3px;
	}
	
	.node-data{
		display: flex;
	}
	.node-data .item{
		flex: 1;
		border: 1px solid #4c4e56;
		font-size: 40px;
		text-align: center;
	}
	.node-data .item .desc{
		font-size: 12px;
	}
	.level-rank table{
		color:#c4ced8;
		width:100%;
		text-align: center;
	}
	.level-rank table tr td{
		padding: 3px;
	}
</style>
</head>
<body>
	<div class="header">
		<ul>
			<li>首页</li>
			<li>实时监测</li>
			<li>排行榜</li>
			<li>最新新闻</li>
		</ul>
	</div>
	<div class="body-container">
		<div class="left-map" id="map">
			右侧地图栏
		</div>
		<div class="right-slide">
			<div class="search-input">
				<input type="text" placeholder="请输入节点名称">
			</div>
			<div class="current-node">
				<div style="margin:15px 0;">当前节点: <span id="nodeName">望华楼</span></div>
				<div class="node-data">
					<div class="item">
						<p id="aqi">38</p>
						<span class="desc">AQI指数</span>
					</div>
					<div class="item">
						<p id="level">12</p>
						<span class="desc">全国排名</span>
					</div>
				</div>
			</div>
			<div class="node-lists">
				<ul style="padding: 0;">
					<li>1号节点</li>
					<li>2号节点</li>
					<li>3号节点</li>
					<li>4号节点</li>
				</ul>
			</div>
			<div class="level-rank">
				<table>
					<thead>
						<tr>
							<td>排名</td>
							<td>城市</td>
							<td>省份</td>
							<td>AQI</td>
						</tr>
					</thead>
					<tbody>
						<tr><td>1</td><td>安徽</td><td>池州</td><td>123</td></tr>
						<tr><td>1</td><td>安徽</td><td>池州</td><td>123</td></tr>
						<tr><td>1</td><td>安徽</td><td>池州</td><td>123</td></tr>
						<tr><td>1</td><td>安徽</td><td>池州</td><td>123</td></tr>
						<tr><td>1</td><td>安徽</td><td>池州</td><td>123</td></tr>
						<tr><td>1</td><td>安徽</td><td>池州</td><td>123</td></tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script>
	var map = new BMap.Map("map", {
        maxZoom : 19,
        minZoom:1,
        mapType : BMAP_NORMAL_MAP
    });// 默认卫星地图BMAP_SATELLITE_MAP
    map.setMapStyle({style:'midnight'});
	var point = new BMap.Point(117.541916, 30.703908);// 地图的中心点
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.centerAndZoom(point, 13);
	var point_list = [];
	var maxResult = -1;
	for(var i = 0; i < 10; ++i) {
		var node = {};
    	node["lng"] = 117.541916 + 0.0001 * i;
    	node["lat"] = 30.703908 - 0.0001 * i;
    	node["count"] = 30;
    	if(node["count"] > maxResult){
        	maxResult = node["count"] + 5;
    	}
    	point_list.push(node);
	}
	var markerArr = new Array();
	var infoWindowArr = new Array();
	for(var i = 0; i < 10; ++i) {
    	var point = new BMap.Point(117.541916 + Math.random() * 0.1,  30.703908 - 0.01 * i);  // 创建点坐标
    	var sContent =
            "<div><h5>测试点"+i+"</h5>" +
            "<h6>PM2.5:"+parseInt(Math.random() * 100)+"</h6>" +
            "<h6>SO2:"+parseInt(Math.random() * 100)+"</h6>" +
            "<h6>PM10:"+parseInt(Math.random() * 100)+"</h6>" +
            "<h6>AQI:"+parseInt(Math.random() * 100)+"</h6><h6><a href='/historical_device_data_list/1'>查看历史数据</a></h6>" +
            "</div>";
    	var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
    	var marker = new BMap.Marker(point);
    	infoWindowArr.push(infoWindow);
    	markerArr.push(marker);
	}
	for(var i = 0; i < infoWindowArr.length; ++i){
    	map.addOverlay(markerArr[i]);
    	var ope = new markerClick(i);
    	markerArr[i].addEventListener("click", ope.clickFunc);
	}
	function markerClick(i){
	    this.clickFunc = function() {
	        console.log(i);
	        this.openInfoWindow(infoWindowArr[i]);
	        var nodeName = document.querySelector("#nodeName");
	        var aqi = document.querySelector("#aqi");
	        var level = document.querySelector("#level");
	        nodeName.innerHTML = "测试点" + i;
	        aqi.innerHTML = parseInt(Math.random() * 100);
	        level.innerHTML = parseInt(Math.random() * 100);
	    }
	}
	var opts = {// 添加控制控件
			type : BMAP_NAVIGATION_CONTROL_SMALL,
			showZoomInfo : true
		};
	map.addControl(new BMap.NavigationControl(opts));
	map.enableScrollWheelZoom(); // 启动鼠标滚轮操作
	map.enableContinuousZoom(); // 开启连续缩放效果
	map.enableInertialDragging(); // 开启惯性拖拽效果
	</script>
</body>
</html>