<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>新建、编辑渠道</title>
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
							<a href="#">渠道管理</a>
							<span class="divider">
								<i class="icon-angle-right arrow-icon"></i>
							</span>
						</li>
						<li>
							<a href="${gameDomain}/gameAdmin/channel/list">渠道列表</a>
							<span class="divider">
								<i class="icon-angle-right arrow-icon"></i>
							</span>
						</li>
						<li class="active">新建渠道</li>
					</ul><!--.breadcrumb-->
				</div>
				<!-- 横向的导航结束 -->
				<div class="page-content">
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							
							<h3 class="header smaller lighter green">添加/修改渠道信息</h3>
							
							<div id="errorBar" class="alert alert-block <c:if test="${errorCode=='1'}">alert-success</c:if><c:if test="${errorCode!='1'}">alert-error</c:if> <c:if test='${empty errorMsg}'> hide</c:if>">
								<button type="button" class="close" data-dismiss="alert">
									<i class="icon-remove"></i>
								</button>
								<p>${errorMsg}</p>
							</div>
							<form class="form-horizontal" name="channelForm" action="${gameDomain}/gameAdmin/channel/save" method="post">
								<input type="hidden" value="${channel.id}" name="channelId" />
								<div class="control-group">
									<label class="control-label" for="form-field-name">渠道名称</label>
									<div class="controls">
										<input type="text" id="form-field-name" name="name" value="${channel.name}" placeholder="渠道全称" />
									</div>
								</div>
								
								<div class="control-group row-fluid">
									<label class="control-label" for="form-field-1">渠道状态</label>
									<div class="controls" >
										<label class="inline">
											<input name="status" type="radio"  value="1" checked/>
											<span class="lbl">使用中</span>
										</label>

										<label class="inline">
											<input name="status" type="radio" value="0"/>
											<span class="lbl">停用</span>
										</label>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="form-field-tags">渠道属性值</label>
									<div class="controls">
										<input type="text" id="form-field-tags" placeholder="请输入渠道配置的参数名称 ..." />
									</div>
									<input type="hidden" name="proNameStr" id="proNameStr"/>
									<c:set var="propertyNames"/>
									<c:forEach items="${channel.proNames}" var="pro" varStatus="p">
										<c:set var="propertyNames" value="${propertyNames},${pro.properyName}|${pro.id}"/>
									</c:forEach>
								</div>
								<div class="form-actions">
									<button class="btn btn-success" type="submit">
										<i class="icon-ok bigger-110"></i>保存
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" onclick="window.location.href='${gameDomain}/gameAdmin/channel/list'">
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
		<script src="${staticJsDomain}/js/bootstrap-tag.min.js"></script>
		<script>
			$(function(){
				$("input[name='status'][type='radio'][value='${channel.status}']").attr("checked", "checked");
				tagInputSetting();
				$("form").submit(function(){
					var s='';
					$('.tag').each(function(i, e){
						var name=$(this).contents().filter(function(){return this.nodeType == 3;}).text();
						var id=$(this).children(0).attr("id");
						s += id ? (name+"|"+id+',') : (name+',');
					});
					s=s.substring(0, s.length-1);
					$("#proNameStr").val(s);
					if(s==''){
						$("#errorBar p").html("渠道属性不能为空");
						$("#errorBar").removeClass("hide");
						return false;
					}
				});
			});
			
			function tagInputSetting(){
				var tag_input = $('#form-field-tags');
				if(! ( /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase())) ) 
					tag_input.tag({placeholder:tag_input.attr('placeholder')});
				else {
					tag_input.after('<textarea id="'+tag_input.attr('id')+'" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
				} 
				$("${propertyNames}".split(",")).each(function(i, e){
					if(i==0){
						return true;
					}
					var arr=e.split("|");
					$("#form-field-tags").before("<span class='tag'>"+arr[0]+"<button id="+arr[1]+" class='close' type='button'>&times;</button></span>");
				});
				$("button[class='close'][type='button']").click(function(){
					$(this).parent().remove();
				});
			}
		</script>
	</body>
</html>
