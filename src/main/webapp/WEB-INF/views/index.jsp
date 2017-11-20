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
	.body-container{
		position:relative;
		margin-top: 70px;
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
		height: 100%;
		overflow: scroll;
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
	    margin: 5px 1px;
	    cursor:pointer;
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
	<jsp:include page="headerMenu.jsp" flush="true"/><!--动态包含-->  
	<div class="body-container">
		<div class="left-map" id="map">
			左侧地图蓝
		</div>
		<div class="right-slide" id="right-data">
			<div class="search-input">
				<input type="text" placeholder="请输入节点名称">
			</div>
			<div class="current-node">
				<div style="margin:15px 0;">当前节点: <span id="nodeName">{{currendNode.nodeName}}</span></div>
				<div class="node-data">
					<div class="item">
						<p id="aqi">{{currendNode.AQI}}</p>
						<span class="desc">AQI指数</span>
					</div>
					<div class="item">
						<p id="level">{{currendNode.rank}}</p>
						<span class="desc">全国排名</span>
					</div>
				</div>
			</div>
			<div class="node-lists">
				<ul style="padding: 0;margin-bottom: 0;">
					<li v-for="node in nodeList" style="margin:5px, 0;">{{node.nodeName}}</li>
				</ul>
				<p style="font-size: 12px;text-align: right;cursor:pointer;" onclick="window.location.href='/WsnWeb/node_list/'">更多>><p>
			</div>
			<div class="level-rank">
				<table>
					<thead>
						<tr>
							<td>排名</td>
							<td>城市</td>
							<td>观测点</td>
							<td>AQI</td>
						</tr>
					</thead>
					<tbody>
						<tr v-for="node in rankList"><td>{{node.rank}}</td><td>{{node.city}}</td><td>{{node.nodeName}}</td><td>{{node.AQI}}</td></tr>
					</tbody>
				</table>
				<p style="font-size: 12px;text-align: right;cursor:pointer;color: #e9eaec;margin-top: 10px;">更多>><p>
			</div>
		</div>
	</div>
	<script>
		var rightData = new Vue({
			el: "#right-data",
			data: {
				nodeList: '',
				currendNode: {
					"nodeName": "",
					"AQI": '',
					"rank": ''
				},
				rankList: []
			},
			created:function(){
				this.init();
			},
			methods:{
				init() {
					this.$http.get('/WsnWeb/api/node_list').then(function(res){
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
					this.$http.get('/WsnWeb/api/node_rank').then(function(res){
		  		    	console.log(res.data);
		  				if(res.status != 200){
		  					this.tip = true;
		  				}else{
		  					this.rankList = res.data;
		  					if(res.data.length > 0){
		  						this.currendNode = res.data[0];
		  					}
		  					loadMap(res.data);
		  				}
		  			}, function(err){
		  				if(err.status != 200){
		  					this.tip = true;
		  				}
		  			});	
				}
			}
		})
	function loadMap(rankList){
		rankList = rankList || [];	
		var nodeNum = rankList.length;
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
		for(var i = 0; i < nodeNum; ++i) {
			var node = {};
    		node["lng"] = rankList[i].longitude;
    		node["lat"] = rankList[i].latitude;
    		point_list.push(node);
		}
		var markerArr = new Array();
		var infoWindowArr = new Array();
		for(var i = 0; i < nodeNum; ++i) {
    		var point = new BMap.Point(rankList[i].longitude,  rankList[i].latitude);  // 创建点坐标
    		var sContent =
            "<div><h5>"+rankList[i].nodeName+"</h5>" +
            "<h6>PM2.5 : "+rankList[i].AQI+"</h6>" +
            "<h6>SO2 : "+rankList[i].So2+"</h6>" +
            "<h6>PM10 : "+rankList[i].Pm10+"</h6>" +
            "<h6>AQI : "+rankList[i].Pm25+"</h6><h6><a href='/historical_device_data_list/" + rankList[i].id+"'>查看历史数据</a></h6>" +
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
		
		var opts = {// 添加控制控件
				type : BMAP_NAVIGATION_CONTROL_SMALL,
				showZoomInfo : true
			};
		map.addControl(new BMap.NavigationControl(opts));
		map.enableScrollWheelZoom(); // 启动鼠标滚轮操作
		map.enableContinuousZoom(); // 开启连续缩放效果
		map.enableInertialDragging(); // 开启惯性拖拽效果
		function markerClick(i){
		    this.clickFunc = function() {
		        this.openInfoWindow(infoWindowArr[i]);
		       	// 更新右侧的当前节点的信息
		        var nodeName = document.querySelector("#nodeName");
		        var aqi = document.querySelector("#aqi");
		        var level = document.querySelector("#level");
		        nodeName.innerHTML = rankList[i].nodeName;
		        aqi.innerHTML = rankList[i].AQI;
		        level.innerHTML = rankList[i].rank;
		    }
		}
	}
	</script>
</body>
</html>