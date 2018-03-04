<#include "base.ftl"/>
<title>新增节点</title>
<style>
	#node-add div{
		margin: 10px;
		padding: 10px;
		text-align: center;
	}
    #node-add span{
		width: 100px;
		text-align: right;
		display: inline-block;
		margin-right: 10px;
	}
</style>
</head>
<body>
<#include "headerMenu.ftl"/>
<div class="body-container content-body-container nodeList-body-container" id="node-add" style="margin-top: 100px;">
	<form action="#" onsubmit="return addNode();" method="post">
		<div>
			<span>Address: </span><input type="text" id =  "address">
		</div>
        <div>
            <span>installTime: </span><input type="text" id =  "installTime">
        </div>
        <div>
            <span>latitude: </span><input type="text" id =  "latitude">
        </div>
        <div>
            <span>longitude: </span><input type="text" id =  "longitude">
        </div>
        <div>
            <span>nodeName: </span><input type="text" id =  "nodeName">
        </div>
        <div>
            <span>city: </span><input type="text" id =  "city">
        </div>
        <div>
            <span>province: </span><input type="text" id =  "province">
        </div>
        <div><input type="submit" value="提交" id="addBtn"></div>
	</form>
</div>
<script>
	$("#node-add input").on("blur", function(){
	   return false;
	});
    $("#node-add input").on("focus", function(){
        return false;
    });
	let $addBtn = $("#addBtn");
		$addBtn.on("click", function(){
	});
	function addNode(){
	    alert("addNode");
	    let address = $("#address").val();
	    let installTime = $("#installTime").val();
	    let latitude = $("#latitude").val();
	    let longitude = $("#longitude").val();
	    let nodeName = $("#nodeName").val();
	    let city = "池州";
	    let province = "安徽";
	    $.ajax({
			url: '/WsnWeb/api/node_add/',
			type: 'POST',
			data: {
                address: address,
                installTime: installTime,
                latitude: latitude,
                longitude: longitude,
                nodeName: nodeName,
                city: city,
                province: province
			},
			success:function(res){
			    alert("I am success");
			    console.log(res);
			},
			error:function(res){
                alert("I am error");
			    console.log(res);
			}
		});
	    return false;
	}
</script>
</body>
</html>