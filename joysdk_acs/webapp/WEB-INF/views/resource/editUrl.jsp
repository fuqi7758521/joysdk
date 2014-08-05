<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>新建、编辑URL</title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/headerCss.jsp"%>
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
							<a href="#">资源管理</a>
							<span class="divider">
								<i class="icon-angle-right arrow-icon"></i>
							</span>
						</li>
						<li>
							<a href="<%=request.getContextPath() %>/acsAdmin/resource/listUrl">URL列表</a>
							<span class="divider">
								<i class="icon-angle-right arrow-icon"></i>
							</span>
						</li>
						<li class="active">新建、更行URL</li>
					</ul><!--.breadcrumb-->
				</div>
				<!-- 横向的导航结束 -->
				<div class="page-content">
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							
							<h3 class="header smaller lighter green">添加/修改URL信息</h3>
							
							<div id="errorBar" class="alert alert-block <c:if test="${errorCode=='1'}">alert-success</c:if><c:if test="${errorCode!='1'}">alert-error</c:if> <c:if test='${empty errorMsg}'> hide</c:if>">
								<button type="button" class="close" data-dismiss="alert">
									<i class="icon-remove"></i>
								</button>
								<p>${errorMsg}</p>
							</div>
							<form class="form-horizontal" name="gameForm" action="<%=request.getContextPath() %>/acsAdmin/resource/saveUrl" method="post">
								<input type="hidden" value="${url.id}" name="id" />
								<div class="control-group">
									<label class="control-label" for="form-field-name">URL地址</label>
									<div class="controls">
										<input type="text" value="${url.url}" id="form-field-name" name="url" placeholder="url地址" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-name">URL说明</label>
									<div class="controls">
										<input type="text" value="${url.des}" id="form-field-name" name="des" placeholder="url说明" />
									</div>
								</div>
								<div class="form-actions">
									<button class="btn btn-success" type="submit">
										<i class="icon-ok bigger-110"></i>保存
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" onclick="window.location.href='<%=request.getContextPath()%>/acsAdmin/resource/listUrl'">
										<i class="icon-reply bigger-110"></i>返回
									</button>
								</div>
							</form>
							<!--PAGE CONTENT ENDS-->
						</div><!--/.span-->
					</div><!--/.row-fluid-->
				</div><!--/.page-content-->

			</div><!--/.main-content-->
		</div><!--/.main-container-->
		<%@ include file="/WEB-INF/views/foot.jsp"%>
		<script>
			$(function(){
				
				$("form").submit(function(){
					var errorMsg="";
					if($("#form-field-name").val()==""){
						errorMsg="请填写url地址";
					}
					if(errorMsg != ""){
						$("#errorBar p").html(errorMsg);
						$("#errorBar").removeClass("hide");
						return false;
					}
				});
			});
		</script>
	</body>
</html>
