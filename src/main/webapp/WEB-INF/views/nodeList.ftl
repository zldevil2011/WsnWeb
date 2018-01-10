<#include "base.ftl"/>
<title>节点列表</title>
<style type="text/css">
.body-container{
	width: 80%;
	margin: 0 auto;
}
.city-head{
	margin-top:100px;
	height:50px;
	line-height: 50px;
	font-size:24px;
	background-color: rgba(87, 179, 130, 0.2);
	text-indent:10px;
}
.search-head{
	height:42px;
	line-height: 42px;
	background-color: #efefef;
}
.search-head span{
	display: inline-block;
	width: 120px;
	text-align: center;
	cursor: pointer;
}
.search-head span.active{
	background-color: #dfdfdf;
}
.left-head{
	float: left;
    display: inline;
    display: inline;
    width: 120px;
    font-size: 24px;
    margin-top: 8px;
    color: #c8c8c8;
    text-align:center;
}
.right-content{
	float: left;
    display: inline;
    padding: 6px 0px 15px 0px;
    margin-top: 10px;
    margin-bottom: 0;
}
.right-content a{
	padding: 4px 9px;
    margin-top: 5px;
    margin-right: 1px;
    cursor:pointer;
    text-decoration:none;
}
.list-data .item{
	display: block;
	margin-bottom:0;
	overflow:hidden;
}
.list-data .item a:hover{
	background-color:rgba(0,0,0,0.1);
}
</style>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container" id="node-list">
	<div class="city-head">
		选择节点
	</div>
	<hr>
	<div>
		<p><span style="font-weight: bold;">关注节点</span>：池州港 望华楼</p>
	</div>
	<hr>
	<div class="search-head">
		<span v-bind:class="{ active: cityActive}" v-on:click="city=true;cityActive=true;nodeActive=false;">按地区</span><span v-bind:class="{ active: nodeActive}" v-on:click="city=false;cityActive=false;nodeActive=true;">按节点</span>
	</div>
	<div class="list-data">
		<dl class="item" v-show="city">
			<dt class="left-head">安徽池州</dt>
			<dd class="right-content">
				<a v-for="node in nodeList" v-bind:href="'/WsnWeb/node_data/'+node.id">{{node.nodeName}}</a>
			</dd>
		</dl>
		<dl class="item" v-for="nodeList in charNodeList" v-show="!city">
			<dt class="left-head">{{nodeList.tip}}</dt>
			<dd class="right-content">
				<a v-for="node in nodeList.node_list">{{node.nodeName}}</a>
			</dd>
		</dl>
	</div>
</div>
<script>
	var nodeList = new Vue({
		el:"#node-list",
		data:{
			nodeList: '',
			charNodeList: '',
			city: true,
			cityActive: true,
			nodeActive: false
		},
		created:function(){
			this.init();
		},
		methods:{
			init:function(){
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
				this.$http.get('/WsnWeb/api/node_location_list').then(function(res){
	  		    	console.log(res.data);
	  				if(res.status != 200){
	  					this.tip = true;
	  				}else{
	  					this.charNodeList = [];
	  					var data = res.data;
	  					var len = 26;
	  					for(let i = 0; i < len; ++i){
	  						var t = String.fromCharCode(65+i);
	  						var tmp = {};
	  						tmp["tip"] = t;
	  						tmp["node_list"] = data[i];
	  						this.charNodeList.push(tmp);
	  					}
	  					console.log(this.charNodeList);
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