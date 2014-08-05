<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>渠道列表</title>
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
						<li class="active">渠道列表</li>
					</ul><!--.breadcrumb-->
				</div>
				<!-- 横向的导航结束 -->
				<div class="page-content">
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
								<h3 class="header smaller lighter green">渠道列表</h3>
								<p class="text-right"><a href="${gameDomain}/gameAdmin/channel/add">
								<button class="btn btn-primary">
                                       <i class="icon-plus bigger-125"></i>添加渠道
                                 </button></a></p>
								<div class="space"></div>
								
								<div class="row-fluid">
								<div class="span12">
									<table id="sample-table-1" class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th class="center">渠道号</th>
												<th>渠道名称</th>
												<!-- <th>父渠道</th> -->
												<th>状态</th>
												<th>操作</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${channels.result}" var="channel">
												<tr>
													<td class="center">${channel.id}</td>
													<td>${channel.name}</td>
													<!-- <td>${channel.pid}</td> -->
													<td>
														<c:if test="${channel.status==0}">
															<span class="label label-inverse">已废弃</span>
														</c:if>
														<c:if test="${channel.status==1}">
															<span class="label label-success">使用中</span>
														</c:if>
													</td>
													<td>
														<div class="hidden-phone visible-desktop btn-group">
														<a class="margin-r2" href="${gameDomain}/gameAdmin/channel/edit/${channel.id}">
														<button class="btn btn-mini btn-info">
															<i class="icon-edit bigger-120"></i>
														</button></a>
													</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div><!--/span-->
								 <jsp:include page="/WEB-INF/views/paging.jsp">
						          <jsp:param name="currentPage" value="${channels.currentPage}"/> 
						          <jsp:param name="pageSize" value="${channels.size}"/> 
						          <jsp:param name="totalCount" value="${channels.totalCount}"/>
						          <jsp:param name="maxPage" value="${channels.pageCount}"/>
						          <jsp:param name="pageUrl" value="${gameDomain}/gameAdmin/channel/list"/>
						          <jsp:param name="otherParam" value=""/>
						        </jsp:include>
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
