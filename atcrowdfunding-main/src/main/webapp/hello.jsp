<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 
	前端框架：BootStrap
		1、在项目中导入bootstrap的 css样式文件和js文件 和它的依赖包jquery
		2、在需要添加样式的标签的class属性值中使用bootstrap提供的class值
	前端框架：layer
 -->
<link rel="stylesheet" type="text/css"
	href="static/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="static/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript"
	src="static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="static/layer/layer.js"></script>
<title>Insert title here</title>
</head>
<body>
	<table class="table table-striped table-bordered table-hover ">
			<tr>
				<td>11</td>
				<td>12</td>
				<td>13</td>
				<td>14</td>
			</tr>
			<tr class="danger">
				<td>21</td>
				<td>22</td>
				<td>23</td>
				<td>24</td>
			</tr>
			<tr>
				<td>31</td>
				<td>32</td>
				<td>33</td>
				<td>34</td>
			</tr>
	</table>
	
	
	<form action="">
		用户名: <span class="glyphicon glyphicon-heart"></span><input>
	</form>
	
	<hr>
	
	<!-- 栅格系统的容器： -->
	<div class="container">
		<!-- 容器中的行 -->
		<div class="row">
			<div class="col-lg-2 col-md-4 col-sm-6">11</div>		
			<div class="col-lg-2 col-md-4 col-sm-6">12</div>		
			<div class="col-lg-2 col-md-4 col-sm-8">13</div>		
			<div class="col-lg-2 col-md-4 col-sm-4">14</div>		
			<div class="col-lg-2 col-md-4 col-sm-10">15</div>		
			<div class="col-lg-2 col-md-4 col-sm-2">16</div>		
		</div>
		<div class="row">
			<div class="col-lg-2">22</div>		
			<div class="col-lg-2">23</div>		
			<div class="col-lg-2">24</div>		
			<div class="col-lg-2">25</div>		
			<div class="col-lg-2">26</div>		
			<div class="col-lg-2">27</div>		
		</div>
		
	</div>
	
	
	<script type="text/javascript">
	
		//弹出警告框,有一个确定按钮
		//var index = layer.alert("xxxx" ,{title:"确认警告提示：",icon:3});//layer调用方法弹层时也会返回弹层的index索引
		//layer.close(index);
		//弹出一个提示框
		//layer.msg('玩命卖萌中',{time:1000});
		//弹出一个确认框：带确认取消
		//如果弹层的确认取消按钮的点击后的回调方法是我们自己编写的，需要我们自己关闭弹层
		//自定义的事件处理函数中可以接收弹层的index索引，可以调用layer.close(index);关闭弹层
		//layer.confirm("你真的要删除吗?" , {title:"删除确认" , icon:2} , function(index){
		//	alert("删除成功");//删除确认按钮的回调函数
		//	layer.close(index);
		//});
		//弹出一个加载等待层
		var index = layer.msg("加载中..." , {icon:16 , time:10000});
		//发送ajax请求，在请求成功的回调函数中关闭等待加载层：   异步注销，弹出注销等待，注销成功后页面跳转
	</script>
	
	
	
	
</body>
</html>