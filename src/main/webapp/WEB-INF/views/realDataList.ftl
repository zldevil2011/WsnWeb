<#include "base.ftl"/>
<link href="https://cdn.bootcss.com/highcharts/6.0.3/css/highcharts.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/highcharts/6.0.3/highcharts.js"></script>
<title>实时数据</title>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container realDataList-body-container" style="margin-top:100px;">
	<div class="real-data" id="real-data">
		<div class="search-box">
			<form class="form-inline">
				<div class="form-group">
					<label for="province">省份</label>
					<input type="text" class="form-control" id="province" placeholder="所在省份">
				</div>
				<div class="form-group">
					<label for="name">名称</label>
					<input type="text" class="form-control" id="name" placeholder="站点名称">
				</div>
				<button type="submit" class="btn btn-default">查找</button>
			</form>
		</div>
		<div class="device-real-data">
			<table class="table table-bordered">
				<thead>
				<tr>
					<th>#</th>
					<th>状态</th>
					<th>地区</th>
					<th>站点</th>
					<th>监测时间</th>
					<th>PM2.5(ug/m³)</th>
					<th>PM10(ug/m³)</th>
					<th>SO2(ug/m³)</th>
					<th>NO2(ug/m³)</th>
					<th>CO(ug/m³)</th>
					<th>O3(ug/m³)</th>
				</tr>
				</thead>
				<tbody>
				<tr v-for="(data,index) in nodeList">
					<th scope="row">{{index}}</th>
					<td>{{data.status}}</td>
					<td>{{data.address}}</td>
					<td>{{data.name}}</td>
					<td>{{data.time}}</td>
					<td>{{data.pm25}}</td>
					<td>{{data.pm10}}</td>
					<td>{{data.so2}}</td>
					<td>{{data.no2}}</td>
					<td>{{data.co}}</td>
					<td>{{data.o3}}</td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script>
    var readData = new Vue({
        el: "#real-data",
        data: {
            nodeList: [{
                status:'正常',
				address:'安徽池州',
				name:'岐山医药',
				pm25:'13',
				pm10:'103',
				so2:'',
				no2:'',
				co:'',
				o3:''
			}]
        },
        created: function () {
            this.init();
        },
		methods:{
            init:function(){

			}
		}
    });
</script>
</body>
</html>