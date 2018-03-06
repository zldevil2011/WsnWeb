<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=WZVN4DbW3hfB9MSgMmwVQBHU8ZV8jRxW"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
    <title>热力图功能示例</title>
    <style type="text/css">
        ul,li{list-style: none;margin:0;padding:0;float:left;}
        html{height:100%}
        body{height:100%;margin:0px;padding:0px;font-family:"微软雅黑";}
        #container{height:500px;width:100%;}
        #r-result{width:100%;}
    </style>
</head>
<body>
<div id="container"></div>
<div id="r-result">
    <input type="button"  onclick="openHeatmap();" value="显示热力图"/><input type="button"  onclick="closeHeatmap();" value="关闭热力图"/>
</div>
</body>
</html>
<script type="text/javascript">
    var map = new BMap.Map("container");          // 创建地图实例

    var point = new BMap.Point(116.418261, 39.921984);
    map.centerAndZoom(point, 15);             // 初始化地图，设置中心点坐标和地图级别
    map.enableScrollWheelZoom(); // 允许滚轮缩放

    var points =[
        {"lng":116.418261,"lat":39.921984,"count":50},
        {"lng":116.423332,"lat":39.916532,"count":51},
        {"lng":116.419787,"lat":39.930658,"count":15},
        {"lng":116.418455,"lat":39.920921,"count":40},
        {"lng":116.418843,"lat":39.915516,"count":100},
        {"lng":116.42546,"lat":39.918503,"count":6},
        {"lng":116.423289,"lat":39.919989,"count":18},
        {"lng":116.418162,"lat":39.915051,"count":80},
        {"lng":116.422039,"lat":39.91782,"count":11},
        {"lng":116.41387,"lat":39.917253,"count":7},
        {"lng":116.41773,"lat":39.919426,"count":42}];
    var mulCnt = 1;
    var tt = setInterval(function(){
        console.log("tt" + new Date());
        var datas = [];
        points.forEach(function(p){
            let t = p;
            t.count = t.count + mulCnt;
            t.lng += 0.001;
            t.lat += 0.0011;
            datas.push(t);
        });
        console.log(datas);
        mulCnt *= ((Math.random() - 1) * 100);
        heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
        map.clearOverlays();
        map.addOverlay(heatmapOverlay);
        heatmapOverlay.setDataSet({data:datas,max:100});
        heatmapOverlay.show();
    }, 1000);

    //是否显示热力图
//    function openHeatmap(){
//        heatmapOverlay.show();
//    }
//    function closeHeatmap(){
//        heatmapOverlay.hide();
//    }
//    openHeatmap();

</script>