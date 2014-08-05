<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>添加、上传证书</title>
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
							<a href="${gameDomain}/gameAdmin/keystore/list">签名证书管理</a>
							<span class="divider">
								<i class="icon-angle-right arrow-icon"></i>
							</span>
						</li>
						<li class="active">新建/上传签名证书</li>
					</ul><!--.breadcrumb-->
				</div>
				<!-- 横向的导航结束 -->
				<div class="page-content">
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							
							<h3 class="header smaller lighter green">新建/上传签名证书</h3>
							
							<div id="errorBar" class="alert alert-block <c:if test="${errorCode eq 1}">alert-success</c:if><c:if test="${errorCode ne 1}">alert-error</c:if> <c:if test='${empty errorMsg}'> hide</c:if>">
								<button type="button" class="close" data-dismiss="alert">
									<i class="icon-remove"></i>
								</button>
								<p>${errorMsg}</p>
							</div>
							<form class="form-horizontal" name="form" action="${gameDomain}/gameAdmin/keystore/save" method="post">
								<div class="control-group">
									<label class="control-label" for="form-field-name">证书名称</label>
									<div class="controls">
										<input type="text" id="form-field-name" name="name" value="${keystore.name}" placeholder="签名证书中文名，方便记忆" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-alias">证书别名</label>
									<div class="controls">
										<input type="text" id="form-field-alias" name="alias" value="${keystore.alias}" placeholder="证书别名" />
									</div>
								</div>
								<c:if test="${not empty fileId}">
								<div class="control-group">
									<label class="control-label" for="form-field-3">证书文件标识</label>
									<div class="controls">
										<input type="text" id="form-field-3" disabled="disabled" style="width:260px;" name="fileId" value="${fileId}.keystore" placeholder="服务器生产的证书文件名" />
									</div>
								</div>
								</c:if>
								<div class="control-group">
									<label class="control-label" for="form-field-version">证书密钥库的密码</label>
									<div class="controls">
										<input type="text"id="form-field-version" name="storePass" value="${keystore.storePass}" placeholder="密钥库的密码" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-coin">证书别名条目的密码</label>
									<div class="controls">
										<input type="text" id="form-field-coin" name="mainPass" value="${keystore.mainPass}" placeholder="证书密钥库的密码相同" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-coinRatio">名字与姓氏</label>
									<div class="controls">
										<input type="text" id="form-field-coinRatio" value="${keystore.commonName}"  name="commonName" placeholder="" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-1">组织名称</label>
									<div class="controls">
										<input type="text" id="form-field-1" name="orgName" value="${keystore.orgName}" placeholder="" />
									</div>
								</div>
								<div class="form-actions">
									<button class="btn btn-success" type="submit">
										<i class="icon-ok bigger-110"></i>保存
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" onclick="window.location.href='${gameDomain}/gameAdmin/keystore/list'">
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
				$("input[name='status'][type='radio'][value='${game.status}']").attr("checked", "checked");
				
				$("form").submit(function(){
					var errorMsg="";
					if($("#form-field-name").val()==""){
						errorMsg="请填写证书名称";
					}else if($("#form-field-alias").val()==""){
						errorMsg="请填写证书别名";
					}else if($("#form-field-3").val()=="" && "${not empty keystore.fileId}"){
						errorMsg="证书文件名为空，证书创建失败";
					}else if($("#form-field-version").val()==""){
						errorMsg="证书密钥库的密码";
					}else if($("#form-field-coin").val()==""){
						errorMsg="请填写证书别名条目的密码，建议与证书密钥库的密码相同";
					}else if($("#form-field-coinRatio").val()==""){
						errorMsg="请填写证书的名字与姓氏";
					}else if($("#form-field-1").val()==""){
						errorMsg="请填写证书组织名称";
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
