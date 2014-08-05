<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>我的签名证书</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/headerCss.jsp"%>
	<style>
		.hidefile {
		    cursor:hand;
		    position:relative;
		    right:0px;
		    width:0px;
		    background-color: blue;
		    opacity:0;
		    filter:alpha(opacity=0)
		}
	</style>
	</head>

	<body>
		<%@ include file="/WEB-INF/views/navbar.jsp"%>
		<div class="main-container container-fluid">
			<a class="menu-toggler" id="menu-toggler" href="#">
				<span class="menu-text"></span>
			</a>
			<%@ include file="/WEB-INF/views/leftMenu.jsp"%>
			<div class="main-content">
				<!-- 横向的导航开始 -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li>
							<i class="icon-home home-icon"></i>
							<a href="#">首页</a>
							<span class="divider">
								<i class="icon-angle-right arrow-icon"></i>
							</span>
						</li>
						<li>
							<a href="${gameDomain}/gameAdmin/keystore/list">签名证书管理</a>
							<span class="divider">
								<i class="icon-angle-right arrow-icon"></i>
							</span>
						</li>
						<li class="active">我的签名证书</li>
					</ul><!--.breadcrumb-->
				</div>
				<!-- 横向的导航结束 -->
				<div class="page-content">
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
								<h3 class="header smaller lighter green">我的签名证书</h3>
								<form name="uploadForm" enctype="multipart/form-data" action="${gameDomain}/gameAdmin/keystore/upload" method="post">
								 <input style="width:0px;" type="file" class="hidefile" onchange="this.form.submit()" name="file" id="uploadfile"/>
								<p class="text-right"><a id="addNewLink">
								<button class="btn btn-primary">
                                       <i class="icon-plus bigger-125"></i>添加证书
                                 </button></a>
                                 <a id="uploadLink">
                                 <button class="btn btn-primary">
                                       <i class="icon-cloud-upload bigger-125"></i>上传证书
                                 </button></a>
                                 </p>
                                 </form>
								<div class="space"></div>
								<div id="errorBar" class="alert alert-block alert-success hide">
									<button type="button" class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>
								<p></p>
								</div>
								<div class="row-fluid">
								<div class="span12">
									<table id="sample-table-1" class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th class="center">序号</th>
												<th>证书名称</th>
												<th>证书别名</th>
												<th>证书文件名</th>
												<th>证书密码</th>
												<th>证书主密码</th>
												<th>操作</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${keystores}" var="keystore" varStatus="s">
											<tr>
												<td class="center">${s.index+1}</td>
												<td>${keystore.name}</td>
												<td>${keystore.alias}</td>
												<td>${keystore.fileId}</td>
												<td>${keystore.storePass}</td>
												<td>${keystore.mainPass}</td>
												<td>
													<div class="hidden-phone visible-desktop btn-group">
														<a  class="margin-r2" href="${gameDomain}/gameAdmin/keystore/delete/${keystore.id}">
														<button class="btn btn-mini btn-info">
															<i class="icon-trash bigger-120"></i>
														</button></a>&nbsp;&nbsp;
														<a class="margin-r2" href="${staticHtmlDomain}/keystore/${keystore.fileId}.keystore">
														<button class="btn btn-mini btn-danger">
															<i class="icon-cloud-download bigger-120"></i>
														</button></a>
													</div>
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div><!--/span-->
							</div><!--/row-->
							<!--PAGE CONTENT ENDS-->
						</div><!--/.span-->
					</div><!--/.row-fluid-->
				</div><!--/.page-content-->

			</div><!--/.main-content-->
		</div><!--/.main-container-->
		<%@ include file="/WEB-INF/views/foot.jsp"%>
	</body>
	<script>
		$(function(){
			$("table td:last").find("a:first").click(function(event){
				var _this=this;
				event.preventDefault();
				$.ajax({
						url:$(_this).attr("href"),
						type:"POST",
						success:function(msg){
							$("#errorBar p").html(msg);
							$("#errorBar").removeClass("alert-error").addClass("alert-success").removeClass("hide");
						},
						error:function(){
							$("#errorBar p").html("服务器调用失败");
							$("#errorBar").removeClass("alert-success").addClass("alert-error").removeClass("hide");
						}
				});
			});
			
			$("#uploadLink").click(function(event){
				event.preventDefault();
				$("#uploadfile").click();
			});
			$("#addNewLink").click(function(event){
				event.preventDefault();
				window.location.href="${gameDomain}/gameAdmin/keystore/add";
			});
		});
	</script>
</html>
