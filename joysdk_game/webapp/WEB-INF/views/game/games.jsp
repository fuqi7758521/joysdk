<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>游戏列表</title>
		<meta name="description" content="游戏列表， 添加游戏，修改游戏信息" />
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
						<li class="active">游戏列表</li>
					</ul><!--.breadcrumb-->
				</div>
				<!-- 横向的导航结束 -->
				<div class="page-content">
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
								<h3 class="header smaller lighter green">我的游戏列表</h3>
								<p class="text-right"><a href="${gameDomain}/gameAdmin/game/add">
								<button class="btn btn-primary">
                                       <i class="icon-plus bigger-125"></i>添加游戏
                                 </button></a></p>
								<div class="space"></div>
								
								<div class="row-fluid">
								<div class="span12">
									<table id="sample-table-1" class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th class="center">序号</th>
												<th>游戏名称</th>
												<th>游戏别名</th>
												<th>虚拟币</th>
												<th>当前版本</th>
												<th>状态</th>
												<th>创建时间</th>
												<th>操作</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${gameList}" var="game" varStatus="s">
											<tr>
												<td class="center">${s.index+1}</td>
												<td>${game.name}</td>
												<td>${game.alias}</td>
												<td>1人民币=${game.coinRatio}${game.coin}</td>
												<td>${game.version}</td>
												<td class="hidden-480">
													<c:if test="${game.status==-1}">
														<span class="label label-inverse">已下线</span>
													</c:if>
													<c:if test="${game.status==0}">
														<span class="label label-warning">测试中</span>
													</c:if>
													<c:if test="${game.status==1}">
														<span class="label label-success">运营中</span>
													</c:if>
												</td>
												<td><fmt:formatDate value="${game.dtCreateTime}" pattern ="yyyy-MM-dd HH:mm"/></td>
												<td>
													<div class="hidden-phone visible-desktop btn-group">
														<a class="margin-r2" href="${gameDomain}/gameAdmin/game/edit/${game.id}">
														<button class="btn btn-mini btn-info">
															<i class="icon-edit bigger-120"></i>
														</button></a>&nbsp;&nbsp;
														<a class="margin-r2" href="${gameDomain}/myChannel/add/${game.id}">
														<button class="btn btn-mini btn-danger">
															<i class="icon-plus bigger-120"></i>添加渠道
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
</html>
