<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>增加渠道</title>
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
						<li class="active">添加游戏渠道</li>
					</ul><!--.breadcrumb-->
				</div>
				<!-- 横向的导航结束 -->
				<div class="page-content">
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							<h3 class="header smaller lighter green">
							<i class="icon-bullhorn"></i>选择渠道并填写密钥信息</h3>
							
							<div id="errorBar" class="alert alert-block <c:if test="${errorCode=='1'}">alert-success</c:if><c:if test="${errorCode!='1'}">alert-error</c:if> <c:if test='${empty errorMsg}'> hide</c:if>">
								<button type="button" class="close" data-dismiss="alert">
									<i class="icon-remove"></i>
								</button>
								<p>${errorMsg}</p>
							</div>
							<form class="form-horizontal" name="gameForm"  enctype="multipart/form-data"  action="${gameDomain}/myChannel/channelConfig/save" method="post">
								<input type="hidden" id="gameId" name="gameId" value="${gameId}"/>
								<div class="control-group">
									<label class="control-label" for="form-field-channels">渠道列表</label>
									<div class="controls">
										<select class="chzn-select" id="channelSel" name="channelId" data-placeholder="请选择渠道...">
											<option value=""></option>
											<c:forEach items="${allChannels}" var="channel">
												<c:if test="${channel.status eq 1}">
													<option value="${channel.id}">${channel.name}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<h3 class="header smaller lighter green">
									<i class="icon-bullhorn"></i>渠道自定义参数信息</h3>
								<div class="control-group">
									<label class="control-label" for="packageName">游戏包名</label>
									<div class="controls">
										<input type="text" value="${channelConfig.packageName}" name="packageName" id="packageName" >
									</div>
								</div>	
								<div class="control-group">
									<label class="control-label" for="fileIcon">游戏ICON</label>
									<div class="controls" style="width:350px;">
										<input type="file" id="fileIcon" name="fileIcon"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="fileSplash">游戏闪屏</label>
									<div class="controls" style="width:350px;">
										<input type="file" id="fileSplash" name="fileSplash"/>
									</div>
								</div>
								<div class="form-actions">
									<button class="btn btn-success" id="submitButton" type="submit">
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
		<script src="${staticJsDomain}/js/chosen.jquery.min.js"></script>
		<script>
			$(function(){
				$('#fileIcon, #fileSplash').ace_file_input({
					no_file:'No File ...',
					btn_choose:'请选择',
					btn_change:'重选',
					droppable:true,
					onchange:null,
					whitelist:'gif|png|jpg|jpeg'
				});
				
				$(".chzn-select").chosen();
				$("#channelSel").change(function(){
					var channelId=$(this).val();
					if(!channelId) return;
					 $.ajax({    
					        type:'get',        
					        url:'${gameDomain}/myChannel/showSetting/${gameId}-'+channelId,    
					        cache:false,
					        dataType:'json',    
					        success:function(result){
					        		if(result.success){
					        			$(".noclass").remove();
					        			$("form input").not("#gameId").val("");
					        			$("#properyIds").remove();
					        			$("label span").attr("data-title", "");
					        			var insertHtml="";
					        			var properyIds="";
					        			var properys=result.data.properys;
					        			var channelConfig=result.data.channelConfig;
					        			if(properys){
						        			$(properys).each(function(i, data){
						        				insertHtml+="<div class='control-group noclass'><label class='control-label for='channelPropery_"+data.id+"'>"+data.properyName+"</label>";
						        				insertHtml+="<div class='controls'>";
						        				insertHtml+="<input type='text' id='channelPropery_"+data.id+"' name='channelPropery_"+data.id+"'/>";
						        				insertHtml+="</div></div>";
						        				properyIds+=data.id+"|";
						        			});
						        			properyIds=properyIds.substring(0, properyIds.lastIndexOf("|"));
						        			insertHtml+="<input type='hidden' name='properyIds' id='properyIds' value='"+properyIds+"'/>";
					        				$("form .control-group").first().after(insertHtml);
					        			}
					        			if(channelConfig){
					        				$("#packageName").val(channelConfig.packageName);
					        				if(channelConfig.icon){
					        					$("#fileIcon").next().find("span").attr("data-title", channelConfig.icon);
					        					//添加一个查看连接
					        				}
					        				if(channelConfig.splashPic){
					        					$("#fileSplash").next().find("span").attr("data-title", channelConfig.splashPic);
					        					//添加一个查看连接
					        				}
					        				$(channelConfig.sdkConfigs).each(function(i, config){
					        					$("#channelPropery_"+config.channelPropery.id).val(config.channelProValue);
					        				});
					        				
					        				$("#gameId").after("<input type='hidden' name='channelConfigId' id='channelConfigId' value='"+channelConfig.id+"'/>");
					        			}
				        				
					        		}
					        }    
					    }); 
				});
				
				$("form").submit(function(){
					var errorMsg="";
					if($("#channelSel").val()==''){
						errorMsg="请选择渠道";
					}else{
						$(".noclass input[type=text]").each(function(i, e){
							if($(this).val()==''){
								errorMsg="请填写渠道密钥参数";
							}
						});
					}
					if(errorMsg!=""){
						$("#errorBar p").html(errorMsg);
						$("#errorBar").removeClass("hide");
						return false;
					}
				});
				
				$("#channelSel").val("${channelConfig.channelId}").trigger("liszt:updated");
				$("#channelSel").change();
			});
		</script>
	</body>
</html>
