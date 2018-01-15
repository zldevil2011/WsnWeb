<#include "base.ftl"/>
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>天气情况</title>
</head>
<body>
<#include "headerMenu.ftl"/>
    <div class="body-container content-body-container weather-body-container" style="margin-top:100px;" id="weather">
        <div class="clearfix info-box">
            <div class="flex-box col-md-3 horizontal-center">
                <div class="city_name">
                    <a id="" href="#place-choice" role="button" class="glyphicon glyphicon-edit hander" data-toggle="modal" style="font-size: 16px;color: #fff;text-decoration: none;"></a>
                    <span name="latest_location">{{data.location}}</span>&nbsp;
                </div>
                <p class="date-time-index">
                    {{ data.date }}
                </p>
            </div>
            <div class="flex-box col-md-6 horizontal-center">
                <div class="weather-status">
                    <div class="today-temperature">{{ data.temperature }}℃</div>
                    <p class="today-detail">
                        {{ data.weather }}&nbsp;&nbsp;|&nbsp;&nbsp;风向：{{ data.cloud_direction }}&nbsp;&nbsp;|&nbsp;&nbsp;风速：{{ data.cloud_speed | keepTwoNum}}&nbsp;&nbsp;|&nbsp;&nbsp;湿度：{{ data.humidity }}%
                    </p>
                </div>
            </div>
            <div class="flex-box col-md-3 horizontal-center">
                <div class="warning-index">污染指数</div>
                <div class="warning-value">{{ data.aqi }}</div>
                <div class="horizontal-center">
                    优
                </div>
            </div>
        </div>
        <div class="clearfix week-forecast" style="background-color: rgba(42, 162, 181, 0.1);">
            <div class="clearfix">
                <div class="col-md-2 col-sm-12">
                    <h2 style="font-size: 18px;color: #000000;font-weight: bold;margin-top: 0;">一周天气</h2>
                </div>
                <div class="col-md-10 hidden-sm"></div>
            </div>
            <div class="clearfix week-forecast-days">
                <div class="week-weather" v-for="fore in forecastList">
                    <div>
                        <div>{{ fore.date }}</div>
                        <div>{{ fore.week }}</div>
                        <div>{{ fore.weather }}</div>
                        <div>{{ fore.high_temperature }}℃/{{ fore.low_temperature }}℃</div>
                        <div>风速:{{ fore.cloud }}</div>
                        <div>{{ fore.cloud_speed }}</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="place-choice" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            请选择城市
                        </h4>
                    </div>
                    <div class="modal-body">
                        <div class="clearfix">
                            <div v-for="city in locationList" class="horizontal-center col-md-2 img-rounded hander city-choice-item" v-on:click="changeLocation(city)">
                                {{ city.location }}
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> <button type="button" class="btn btn-primary">确认</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="clearfix" style="background: rgba(228, 228, 219, 0.2);width: 80%; margin: 0 auto;margin-bottom: 100px;">
        <div class="clearfix">
            <div class="col-md-2 col-sm-12 horizontal-text-center" style="text-align: center;">
                <h2 style="font-size: 18px;color: #000000;font-weight: bold;">一周气温趋势</h2>
            </div>
            <div class="col-md-10 hidden-sm"></div>
        </div>
        <div class="clearfix week-weather-line">
            <div id="chartContainer" style="height: 200px; width: 100%;"></div>
        </div>
    </div>
<script>
    var dayMap = {
        "CLEAR_DAY":"晴天",
        "CLEAR_NIGHT":"晴夜",
        "PARTLY_CLOUDY_DAY":"多云",
        "PARTLY_CLOUDY_NIGHT":"多云",
        "CLOUDY":"阴天",
        "RAIN":"雨",
        "SNOW":"雪",
        "WIND":"风",
        "FOG":"雾"
    };
    var dayWeekMap = ['星期天','星期一','星期二','星期三','星期四','星期五','星期六'];
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
    var weather = new Vue({
        el: "#weather",
        data:{
            locationList: [{
                location: '池州',
                longitude: '117.49142',
                latitude: '30.66469'
            }, {
                location: '合肥',
                longitude: '117.17',
                latitude: '31.52'
            }, {
                location: '北京',
                longitude: '116.41667',
                latitude: '39.91667'
            }, {
                location: '西安',
                longitude: '108.95000',
                latitude: '34.26667'
            }],
            data:{
                location: '池州',
                date: '2018-01-15',
                temperature: '23',
                weather: '晴天',
                cloud: '微风',
                cloud_speed: '三',
                humidity: 80,
                pm25: 33
            },
            forecastList:[{
                date: '2018-01-15',
                week: '星期三',
                weather_day: '晴天',
                weather_night: '阴天',
                high_temperature: '10',
                low_temperature: '0',
                cloud: '东风',
                cloud_speed: '三级'
            },{
                date: '2018-01-15',
                week: '星期三',
                weather_day: '晴天',
                weather_night: '阴天',
                high_temperature: '10',
                low_temperature: '0',
                cloud: '东风',
                cloud_speed: '三级'
            },{
                date: '2018-01-15',
                week: '星期三',
                weather_day: '晴天',
                weather_night: '阴天',
                high_temperature: '10',
                low_temperature: '0',
                cloud: '东风',
                cloud_speed: '三级'
            },{
                date: '2018-01-15',
                week: '星期三',
                weather_day: '晴天',
                weather_night: '阴天',
                high_temperature: '10',
                low_temperature: '0',
                cloud: '东风',
                cloud_speed: '三级'
            },{
                date: '2018-01-15',
                week: '星期三',
                weather_day: '晴天',
                weather_night: '阴天',
                high_temperature: '10',
                low_temperature: '0',
                cloud: '东风',
                cloud_speed: '三级'
            }],
            lowTemList:[],
            highTemList:[]
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
        methods:{
            init:function(){
                this.loadRealTime(117.49142, 30.66469, '池州');
                this.loadForecast(117.49142, 30.66469), '池州';
            },
            changeLocation:function(data){
                this.loadRealTime(data.longitude, data.latitude, data.location);
                this.loadForecast(data.longitude, data.latitude, data.location);
            },
            loadRealTime:function (longitude, latitude, location) {
                this.$http.jsonp('https://api.caiyunapp.com/v2/vgAv8bUfzXZMc=ZH/'+longitude+','+latitude+'/realtime.json'
                ).then(function(res){
                    var result = res.data.result;
                    this.data = {
                        location:location,
                        date: new Date().MyTimeFormat("yyyy-MM-dd"),
                        temperature: result.temperature,
                        weather: dayMap[result.skycon],
                        cloud_direction:this.getDirection(result.wind.direction),
                        cloud_speed:result.wind.speed,
                        humidity:result.humidity * 100,
                        pm25:result.pm25,
                        aqi:result.aqi
                    };
                    if(res.status != 200){
                        this.tip = true;
                    }else{

                    }
                }, function(err){
                    if(err.status != 200){
                        this.tip = true;
                    }
                });
            },
            loadForecast:function (longitude, latitude, location) {
                this.$http.jsonp('https://api.caiyunapp.com/v2/vgAv8bUfzXZMc=ZH/'+longitude+','+latitude+'/forecast.json').then(function(res){
                    if(res.status != 200){
                        this.tip = true;
                    }else{
                        var result = res.data.result.daily;
                        this.forecastList = [];
                        this.lowTemList = [];
                        this.highTemList = [];
                        for(var i = 0; i < 5; ++i){
                            var tmpObj = {
                                date: result.aqi[i].date,
                                week: dayWeekMap[new Date(result.aqi[i].date).getDay()],
                                weather: dayMap[result.skycon[i].value],
                                high_temperature:result.temperature[i].max,
                                low_temperature:result.temperature[i].min,
                                cloud:result.wind[i].avg.speed,
                                cloud_speed:this.getDirection(result.wind[i].avg.direction)
                            };
                            var temDate = new Date(result.aqi[i].date);
                            var lowTmp = {};
                            lowTmp = {
                                x: temDate,
                                y: result.temperature[i].min
                            };
                            var highTmp = {};
                            highTmp = {
                                x: temDate,
                                y: result.temperature[i].max
                            };
                            this.lowTemList.push(lowTmp);
                            this.highTemList.push(highTmp);
                            this.forecastList.push(tmpObj);
                        }
                        this.paintLines();
                    }
                }, function(err){
                    if(err.status != 200){
                        this.tip = true;
                    }
                });
            },
            paintLines:function(){
                $("#chartContainer").html('');
                console.log("yes");
                var chart = new CanvasJS.Chart("chartContainer", {
                    animationEnabled: true,
                    theme: "light2",
                    title:{
                        text: ""
                    },
                    axisX:{
                        valueFormatString: "DD MMM",
                        crosshair: {
                            enabled: true,
                            snapToDataPoint: true
                        }
                    },
                    axisY: {
                        title: "温度",
                        crosshair: {
                            enabled: true
                        }
                    },
                    toolTip:{
                        shared:true
                    },
                    legend:{
                        cursor:"pointer",
                        verticalAlign: "bottom",
                        horizontalAlign: "left",
                        dockInsidePlotArea: true,
                        itemclick: toogleDataSeries
                    },
                    data: [{
                        type: "line",
                        showInLegend: true,
                        name: "最高温度",
                        markerType: "square",
                        xValueFormatString: "DD MMM, YYYY",
                        yValueFormatString: "###.#℃",
                        color: "#F08080",
                        dataPoints: this.highTemList
                    }, {
                        type: "line",
                        showInLegend: true,
                        name: "最低温度",
                        lineDashType: "dash",
                        yValueFormatString: "###.#℃",
                        dataPoints: this.lowTemList
                    }]
                });
                chart.render();

                function toogleDataSeries(e){
                    if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                        e.dataSeries.visible = false;
                    } else{
                        e.dataSeries.visible = true;
                    }
                    chart.render();
                }
            },
            getDirection:function(angle){
                if(angle == 0){
                    return "北风";
                }
                if(angle > 0 && angle < 90){
                    return "东北风";
                }
                if(angle == 90){
                    return "东风";
                }
                if(angle > 90 && angle < 180){
                    return "东南风";
                }
                if(angle == 180){
                    return "南风";
                }
                if(angle > 180 && angle < 270){
                    return "西南风";
                }
                if(angle == 270){
                    return "西风";
                }
                return "西北风";
            }
        }
    })
</script>
</body>
</html>