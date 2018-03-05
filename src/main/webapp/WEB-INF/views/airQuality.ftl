<#include "base.ftl"/>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=WZVN4DbW3hfB9MSgMmwVQBHU8ZV8jRxW"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
<!--
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=2E92C953574c3fa5f86de9c14501c0ea"></script>
 -->
<title>污染趋势</title>
<style>
    html{height:100%}
    body{height:100%;margin:0px;padding:0px;font-family:"微软雅黑";}
    #map{height:100%;width:100%;}
</style>
</head>
<body>
	<#include "headerMenu.ftl"/>
	<div id="airQuality" class="airQuality-body-container body-container">
		<div class="left-map" id="map">
			地图蓝
		</div>
        <div class="search-box" style="padding-bottom: 10px;text-align: center;position: absolute;top: 0;right: 0;">
            <p>各观测站点数据（ 更新时间：{{ updateTime }} ）</p>
            <form class="form-inline" onsubmit="return false;">
                <div class="form-group">
                    <label for="dataTime">时间</label>
                    <input type="date" class="form-control" id="startTime" v-model="search_info.startTime">
                    <input type="date" class="form-control" id="endTime" v-model="search_info.endTime">
                </div>
                <div class="form-group">
                    <select class="form-control" v-model="search_info.dataType">
                        <option v-for="dataType in dataType_list" v-bind:value='dataType.value'>{{dataType.name}}</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-success btn-100" style="margin-left: 10px;padding: 5px 12px;" @click="getInfoData">查找</button>
            </form>
        </div>
	</div>
	<script>
        Vue.http.options.emulateJSON = true;
        var t_id = 0;
        var interval_tag = 0;
        var airQuality = new Vue({
            el: "#airQuality",
            data: {
                original_data: '',
                nodeList: '',
                search_info: {
                    startTime: '2018-03-03',
                    endTime: '2018-03-04',
                    dataType: 'all',
                    parameter: 'pm25'
                },
                parameter_list:[{
                    id: 0,
                    name: 'aqi'
                },{
                    id: 1,
                    name: 'pm25'
                },{
                    id: 2,
                    name: 'pm10'
                },{
                    id: 3,
                    name: 'so2'
                }],
                dataType_list:[{
                    id: 0,
                    value: 'all',
                    name: '全部数据'
                },{
                    id: 1,
                    value: 'hour',
                    name: '小时平均'
                },{
                    id: 2,
                    value: 'day',
                    name: '日平均'
                }],
                updateTime: '2018-03-03 12:00:00'
            },
            created:function(){
                this.init();
            },
            methods:{
                init:function(){
                    var that = this;
                    this.$http.get('/WsnWeb/api/node_list').then(function(res){
                        console.log(res.data);
                        if(res.status != 200){
                            this.tip = true;
                        }else{
                            this.nodeList = res.data;
                            this.getInfoData();
                        }
                    }, function(err){
                        if(err.status != 200){
                            this.tip = true;
                        }
                    });
                },
                getInfoData:function() {
                    var that = this;
                    var search_info = this.search_info;
                    var original_data = this.original_data;
                    var nodeList = this.nodeList;
                    console.log(search_info);
                    this.$http.post('/WsnWeb/api/all_nodes_historical_data/', search_info ,{
                        'headers': {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }).then(function(res){
                        if(res.status != 200){

                        }else{
                            console.log(res.data);
                            that.original_data = res.data;
                            loadAirQualityMap(that.original_data, search_info.parameter, nodeList);
                        }
                    }, function(err){
                        if(err.status != 200){
                        }
                    });

                }
            }
        });
        function loadAirQualityMap(airQualityList, parameter, nodeList) {
            clearInterval(interval_tag);
            airQualityList = airQualityList || [];
            var nodeNum = airQualityList.length;
            var points = [];
            var maxResult = -1;
            console.log("NodeNum: " + nodeNum);
            if(nodeNum > 0){
                timeCnt = 0;
                if(airQualityList[0].length > 0){
                    timeCnt = airQualityList[0].length;
                }
            }
            t_id = 0;
            interval_tag = setInterval(function(){
                loadMap(t_id, airQualityList, parameter, nodeList);
                t_id += 1;
                if(t_id >= timeCnt){
                    t_id = 0;
                }
            }, 1000);
        }
        function loadMap(t_id, airQualityList, parameter, nodeList){
            airQualityList = airQualityList || [];
            var nodeNum = airQualityList.length;
            var points = [];
            var maxResult = -1;
            console.log("NodeNum: " + nodeNum);
            for (var i = 0; i < nodeNum; ++i) {
                try {
                    var node = {};
                    node["lng"] = nodeList[i].longitude;
                    node["lat"] = nodeList[i].latitude;
                    console.log("NodeNum1: " + i + ":" + airQualityList[i][t_id]);
                    console.log("NodeNum2: " + i + ":" + airQualityList[i][t_id][parameter]);
                    node["count"] = airQualityList[i][t_id][parameter];
                    points.push(node);
                }catch (e){

                }
            }
            console.log(points);
            var map = new BMap.Map("map");          // 创建地图实例
            var opts = {// 添加控制控件
                type : BMAP_NAVIGATION_CONTROL_SMALL,
                showZoomInfo : true
            };
            map.addControl(new BMap.NavigationControl(opts));
            map.enableScrollWheelZoom(); // 启动鼠标滚轮操作
            map.enableContinuousZoom(); // 开启连续缩放效果
            map.enableInertialDragging(); // 开启惯性拖拽效果

            var point = new BMap.Point(117.54728, 30.70481);
            map.centerAndZoom(point, 15);             // 初始化地图，设置中心点坐标和地图级别
            map.enableScrollWheelZoom(); // 允许滚轮缩放

            if (!isSupportCanvas()) {
                alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
            }
            //详细的参数,可以查看heatmap.js的文档 https://github.com/pa7/heatmap.js/blob/master/README.md
            //参数说明如下:
            /* visible 热力图是否显示,默认为true
             * opacity 热力的透明度,1-100
             * radius 势力图的每个点的半径大小
             * gradient  {JSON} 热力图的渐变区间 . gradient如下所示
             *	{
                    .2:'rgb(0, 255, 255)',
                    .5:'rgb(0, 110, 255)',
                    .8:'rgb(100, 0, 255)'
                }
                其中 key 表示插值的位置, 0~1.
                    value 为颜色值.
             */
            heatmapOverlay = new BMapLib.HeatmapOverlay({"radius": 20});
            map.addOverlay(heatmapOverlay);
            heatmapOverlay.setDataSet({data: points, max: 100});
            heatmapOverlay.show();
            function setGradient() {
                /*格式如下所示:
               {
                     0:'rgb(102, 255, 0)',
                     .5:'rgb(255, 170, 0)',
                     1:'rgb(255, 0, 0)'
               }*/
                var gradient = {};
                var colors = document.querySelectorAll("input[type='color']");
                colors = [].slice.call(colors, 0);
                colors.forEach(function (ele) {
                    gradient[ele.getAttribute("data-key")] = ele.value;
                });
                heatmapOverlay.setOptions({"gradient": gradient});
            }
        }
        //判断浏览区是否支持canvas
        function isSupportCanvas(){
            var elem = document.createElement('canvas');
            return !!(elem.getContext && elem.getContext('2d'));
        }
	</script>
</body>
</html>