<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>新建、编辑游戏</title>
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
							<a href="#">游戏管理</a>
							<span class="divider">
								<i class="icon-angle-right arrow-icon"></i>
							</span>
						</li>
						<li>
							<a href="${gameDomain}/gameAdmin/game/list">游戏列表</a>
							<span class="divider">
								<i class="icon-angle-right arrow-icon"></i>
							</span>
						</li>
						<li class="active">新建游戏</li>
					</ul><!--.breadcrumb-->
				</div>
				<!-- 横向的导航结束 -->
				<div class="page-content">
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							
							<h3 class="header smaller lighter green">添加/修改游戏信息</h3>
							
							<div id="errorBar" class="alert alert-block <c:if test="${errorCode=='1'}">alert-success</c:if><c:if test="${errorCode!='1'}">alert-error</c:if> <c:if test='${empty errorMsg}'> hide</c:if>">
								<button type="button" class="close" data-dismiss="alert">
									<i class="icon-remove"></i>
								</button>
								<p>${errorMsg}</p>
							</div>
							<form class="form-horizontal" name="gameForm" action="${gameDomain}/gameAdmin/game/save" method="post">
								<input type="hidden" value="${gameId}" name="gameId" />
								<div class="control-group">
									<label class="control-label" for="form-field-name">游戏名称</label>
									<div class="controls">
										<input type="text" value="${game.name}" id="form-field-name" name="name" placeholder="游戏名字_版本 如：仙剑_安卓" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-alias">游戏别名</label>
									<div class="controls">
										<input type="text" value="${game.alias}" id="form-field-alias" name="alias" placeholder="游戏的英文名或拼音" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-3">游戏包名</label>
									<div class="controls">
										<input type="text" value="${game.packageName}" id="form-field-3" name="packageName" placeholder="com.xxx.xxx" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-version">版 本 号</label>
									<div class="controls">
										<input type="text" value="${game.version}" id="form-field-version" name="version" placeholder="1.0.2" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-coin">游戏币名</label>
									<div class="controls">
										<input type="text" value="${game.coin}" id="form-field-coin" name="coin" placeholder="元宝" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-coinRatio">虚拟币兑换比例</label>
									<div class="controls">
										<input type="text"  value="${game.coinRatio}" onkeyup="value=this.value.replace(/\D+/g,'')"  id="form-field-coinRatio" name="coinRatio" placeholder="100" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-1">游戏官网</label>
									<div class="controls">
										<input type="url" value="${game.website}" id="form-field-1" name="website" placeholder="http://xxx.xxx.com" />
									</div>
								</div>
								<div class="control-group row-fluid">
									<label class="control-label" for="form-field-1">游戏状态</label>
									<div class="controls" >
										<label class="inline">
											<input name="status" type="radio"  value="0" checked/>
											<span class="lbl">测试中</span>
										</label>

										<label class="inline">
											<input name="status" type="radio" value="1"/>
											<span class="lbl">运营中</span>
										</label>

										<label class="inline">
											<input name="status" type="radio" value="-1"/>
											<span class="lbl">已停运</span>
										</label>
									</div>
								</div>
								<div class="form-actions">
									<button class="btn btn-success" type="submit">
										<i class="icon-ok bigger-110"></i>保存
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" onclick="window.location.href='${gameDomain}/gameAdmin/game/list'">
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
						errorMsg="请填写游戏名称";
					}else if($("#form-field-alias").val()==""){
						errorMsg="请填写游戏别名";
					}else if($("#form-field-packageName").val()==""){
						errorMsg="请填写游戏包名";
					}else if($("#form-field-version").val()==""){
						errorMsg="请填写游戏版本号";
					}else if($("#form-field-coin").val()==""){
						errorMsg="请填写游戏币名称";
					}else if($("#form-field-coinRatio").val()==""){
						errorMsg="请填写游戏币兑换比例";
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
