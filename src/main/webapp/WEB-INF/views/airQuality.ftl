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
    .color-block{
        width: 40px;
        height: 18px;
        font-size: 12px;
        border-radius: 3px;
        text-align: center;
        display: inline-block;
    }
    .color-0{  background-color: rgb(0, 97, 0);  }
    .color-2{  background-color: rgb(255, 102, 0);  }
    .color-4{  background-color: rgb(255, 204, 0);  }
    .color-6{  background-color: rgb(255, 255, 0);  }
    .color-8{  background-color: rgb(122, 171, 0);  }
    .color-10{  background-color: rgb(255, 34, 0);  }
</style>
</head>
<body>
	<#include "headerMenu.ftl"/>
	<div id="airQuality" class="airQuality-body-container body-container" style="margin-top: 70px;">
		<div class="left-map" id="map" style="display: flex;justify-content: center; align-items: center;">
            <img alt="" src="/WsnWeb/img/loading.gif" v-show="loadingAirQualityData">
		</div>
        <div style="padding-bottom: 10px;text-align: center;position: absolute;top: 0;right: 0;" data-label="parameterList">
            <div class="form-group">
                <select class="form-control" v-model="search_info.parameter" @change="chooseParameter">
                    <option v-for="parameter in parameter_list" v-bind:value='parameter.value'>{{parameter.name}}</option>
                </select>
            </div>
        </div>
        <div id="dataRangeTip" style="position: absolute;left: 10px; bottom: 80px;">
            <span class="color-block color-0">0</span>
            <span class="color-block color-2">&nbsp;</span>
            <span class="color-block color-4">&nbsp;</span>
            <span class="color-block color-6">&nbsp;</span>
            <span class="color-block color-8">&nbsp;</span>
            <span class="color-block color-10" id="maxValue">&nbsp;</span>
        </div>
        <div class="search-box" style="text-align: center;position: absolute;bottom: 70px;right: 0;background-color: rgba(0,0,0,0.1);border-radius: 10px;padding: 10px;">
            <p style="font-size: 18px; color:red;">各观测站点{{search_info.parameter}}数据（ 数据时间：{{ dataTime }} ）</p>
            <form class="form-inline" onsubmit="return false;">
                <div class="form-group">
                    <input type="date" class="form-control" id="startTime" v-model="search_info.startTime">&nbsp;至
                    <input type="date" class="form-control" id="endTime" v-model="search_info.endTime">
                </div>
                <div class="form-group">
                    <select class="form-control" v-model="search_info.dataType">
                        <option v-for="dataType in dataType_list" v-bind:value='dataType.value'>{{dataType.name}}</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-success btn-100" style="margin-left: 10px;padding: 5px 12px;" @click="getInfoData">查找</button>
                <button type="btn" class="btn btn-danger btn-100" style="margin-left: 10px;padding: 5px 12px;" @click="pause">{{ imgStatusText }}</button>
            </form>
        </div>
	</div>
	<script>
        var map;

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
        var t_id = 0;
        var interval_tag = 0;
        var airQuality = new Vue({
            el: "#airQuality",
            data: {
                loadingAirQualityData: true,
                imgStatus: 'pause',
                imgStatusText: '暂停',
                original_data: '',
                nodeList: '',
                search_info: {
                    startTime: '2018-03-03', //new Date(new Date()-24*60*60*1000).MyTimeFormat("yyyy-MM-dd"),
                    endTime: '2018-03-04', //new Date().MyTimeFormat("yyyy-MM-dd"),
                    dataType: 'hour',
                    parameter: 'pm25'
                },
                parameter_list:[{
                    id: 0,
                    name: 'AQI',
                    value: 'aqi'
                },{
                    id: 1,
                    name: 'Pm2.5',
                    value: 'pm25'
                },{
                    id: 2,
                    name: 'Pm10',
                    value: 'pm10'
                },{
                    id: 3,
                    name: 'So2',
                    value: 'so2'
                },{
                    id: 4,
                    name: 'No2',
                    value: 'no2'
                },{
                    id: 5,
                    name: 'Co',
                    value: 'Co'
                },{
                    id: 6,
                    name: 'O3',
                    value: 'o3'
                },{
                    id: 7,
                    name: '湿度',
                    value: 'airHumidity'
                },{
                    id: 8,
                    name: '温度',
                    value: 'airTemperature'
                },{
                    id: 9,
                    name: '风速',
                    value: 'windSpeed'
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
                updateTime: '2018-03-03 12:00:00',
                dataTime: new Date().MyTimeFormat("yyyy-MM-dd HH:mm:ss")
            },
            created:function(){
                this.init();
            },
            methods:{
                init:function(){
                    var that = this;
                    //获取站点列表
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
                    //加载新日期数据之前，清除原始的数据
                    clearInterval(interval_tag);
                    document.querySelector("#map").innerHTML = '<img alt="" src="/WsnWeb/img/loading.gif" v-show="loadingAirQualityData">';
                    this.loadingAirQualityData = true;
                    var search_info = this.search_info;
                    var nodeList = this.nodeList;

                    var that = this;
                    // 获取每个站点的历史数据
                    this.$http.post('/WsnWeb/api/all_nodes_historical_data/', search_info ,{
                        'headers': {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }).then(function(res){
                        if(res.status != 200){

                        }else{
                            that.loadingAirQualityData = false;
                            that.original_data = res.data;
                            this.loadAirQualityMap(that.original_data, search_info.parameter, nodeList);
                        }
                    }, function(err){
                        if(err.status != 200){
                        }
                    });

                },
                pause:function(){
                    var airQualityList = this.original_data;
                    var parameter = this.search_info.parameter;
                    var nodeList = this.nodeList;
                    if(this.imgStatus === "pause"){
                        clearInterval(interval_tag);
                        this.imgStatus = "start";
                        this.imgStatusText = "开始";
                    }else{
                        this.imgStatus = "pause";
                        this.imgStatusText = "暂停";

                        var that = this;
                        interval_tag = setInterval(function(){
                            if(airQualityList.length > 0){
                                try{
                                    that.dataTime = airQualityList[0][t_id]["updateTime"];
                                }catch (e){

                                }
                            }
                            loadMap(t_id, airQualityList, parameter, nodeList);
                            t_id += 1;
                            if(t_id >= timeCnt){
                                t_id = 0;
                            }
                        }, 1000);
                    }
                },
                loadAirQualityMap:function(airQualityList, parameter, nodeList){
                    // 生成地图并清除之前的数据，按照获取的数据的长度初始化开始进行渲染的时间序列
                    map = new BMap.Map("map");          // 创建地图实例
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


                    clearInterval(interval_tag);
                    airQualityList = airQualityList || [];
                    var nodeNum = airQualityList.length;
                    if(nodeNum > 0){
                        timeCnt = 0;
                        if(airQualityList[0].length > 0){
                            timeCnt = airQualityList[0].length;
                        }
                    }
                    // timeCnt代表了有多少张图
                    // t_id代表当前执行的图的下标
                    t_id = 0;
                    var that = this;
                    interval_tag = setInterval(function(){
                        if(airQualityList.length > 0){
                            try{
                                that.dataTime = airQualityList[0][t_id]["updateTime"];
                            }catch (e){

                            }
                        }
                        // 根据参数加载地图
                        loadMap(t_id, airQualityList, parameter, nodeList);
                        t_id += 1;
                        if(t_id >= timeCnt){
                            t_id = 0;
                        }
                    }, 1000);
                },
                chooseParameter:function(ele){
                    clearInterval(interval_tag);

                    this.search_info.parameter = ele.target.value;
                    var airQualityList = this.original_data;
                    var parameter = this.search_info.parameter;
                    var nodeList = this.nodeList;

                    var that = this;
                    t_id = 0;
                    interval_tag = setInterval(function(){
                        if(airQualityList.length > 0){
                            try{
                                that.dataTime = airQualityList[0][t_id]["updateTime"];
                            }catch (e){}
                        }
                        loadMap(t_id, airQualityList, parameter, nodeList);
                        t_id += 1;
                        if(t_id >= timeCnt){
                            t_id = 0;
                        }
                    }, 1000);
                }
            }
        });
        function loadMap(t_id, airQualityList, parameter, nodeList){
            map.clearOverlays();
            var max = 0;
            airQualityList = airQualityList || [];
            var nodeNum = airQualityList.length;
            var points = [];
            for (var i = 0; i < nodeNum; ++i) {
                try {
                    var node = {};
                    node["lng"] = nodeList[i].longitude;
                    node["lat"] = nodeList[i].latitude;
                    node["count"] = airQualityList[i][t_id][parameter];
                    if(node["count"] != null){
                        max = max > node["count"] ? max : node["count"];
                    }
                    points.push(node);
                }catch (e){

                }
            }
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
            heatmapOverlay = new BMapLib.HeatmapOverlay({"radius": 20}, {gradient:{
                1:'rgb(255, 34, 0)',
                0.8:'rgb(122, 171, 0)',
                0.6:'rgb(255, 255, 0)',
                0.4:'rgb(255, 204, 0)',
                0.2:'rgb(255, 102, 0)',
                0:'rgb(0, 97, 0)'
            } });
            map.clearOverlays();

//            var bdary = new BMap.Boundary();
//            bdary.get("安徽省池州市贵池区", function(rs){       //获取行政区域
//                map.clearOverlays();        //清除地图覆盖物
//                var count = rs.boundaries.length; //行政区域的点有多少个
//                console.log(rs.boundaries);
//                if (count === 0) {
//                    alert('未能获取当前输入行政区域');
//                    return ;
//                }
//                var pointArray = [];
//                for (var i = 0; i < count; i++) {
//                    var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
//                    map.addOverlay(ply);  //添加覆盖物
//                    pointArray = pointArray.concat(ply.getPath());
//                }
//                map.setViewport(pointArray);    //调整视野
//            });

            if(max > 100){
                max = parseInt(max + 50);
            }else if(max > 10){
                max = parseInt(max + 10);
            }else if(max > 0){
                max = parseInt(max + 1);
            }else{
                max = 100;
            }
            document.querySelector("#maxValue").innerHTML = max;
            map.addOverlay(heatmapOverlay);
            heatmapOverlay.setDataSet({data: points, max: max,  min:0});
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