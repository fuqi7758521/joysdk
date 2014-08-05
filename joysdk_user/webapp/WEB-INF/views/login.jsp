<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>卓越SDK 用户登录</title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/headerCss.jsp"%>
	</head>
	<body class="login-layout light-login">
		<div class="main-container container-fluid">
			<div class="main-content">
				<div class="row-fluid">
					<div class="span12">
						<div class="login-container">
							<div class="row-fluid">
								<div class="center">
									<h1>
										<i class="icon-leaf green"></i>
										<span class="red">Ace</span>
										<span class="white">Application</span>
									</h1>
									<h4 class="blue">&copy; Company Name</h4>
								</div>
							</div>

							<div class="space-6"></div>

							<div class="row-fluid">
								<div class="position-relative">
							<div id="login-box" class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="icon-coffee green"></i>
											<span>请输入您的登录信息</span>
										</h4>
										<div class="space-6"></div>
										<form class="form-horizontal" id="login-form" method="post" action="${passportDomain}/login">
											<input type="hidden" name="returnUrl" value="${param.returnUrl}"/>
											<fieldset>
												<label>
													<span class="block input-icon input-icon-right">
														<input type="text" class="span12" value="${param.account}" name="account" id="account" placeholder="Username" />
														<i class="icon-user"></i>
													</span>
												</label>
												<label>
													<span class="block input-icon input-icon-right">
														<input type="password" class="span12" value="${param.password}"  name="password" id="password" placeholder="Password" />
														<i class="icon-lock"></i>
													</span>
												</label>
												<div class="space-2"></div>
												<div class="alert <c:if test="${not empty errorMsg}">alert-error</c:if> <c:if test="${empty errorMsg}"> hide</c:if>">
													<button type="button" class="close" data-dismiss="alert">
														<i class="icon-remove"></i>
													</button>
													<span>${errorMsg}</span>
												</div>
												<div class="clearfix">
													<label class="inline">
														<input type="checkbox" name="remberMe" value="true"/>
														<span class="lbl">下次自动登录</span>
													</label>

													<button type="submit" class="width-35 pull-right btn btn-small btn-primary">
														<i class="icon-key"></i>
														登录
													</button>
												</div>

												<div class="space-4"></div>
											</fieldset>
										</form>

										<div class="social-or-login center">
											<span class="bigger-110">使用其他方式登录</span>
										</div>

										<div class="social-login center">
											<a class="btn btn-primary">
												<i class="icon-facebook"></i>
											</a>

											<a class="btn btn-info">
												<i class="icon-twitter"></i>
											</a>

											<a class="btn btn-danger">
												<i class="icon-google-plus"></i>
											</a>
										</div>
									</div><!--/widget-main-->

									<div class="toolbar clearfix">
										<div>
											<a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link">
												<i class="icon-arrow-left"></i>
												忘记密码
											</a>
										</div>

										<div>
											<a href="${passportDomain}/register" class="user-signup-link">
												立即注册
												<i class="icon-arrow-right"></i>
											</a>
										</div>
									</div>
								</div><!--/widget-body-->
							</div><!--/login-box-->
							</div>
						</div>
					</div><!--/.span-->
				</div><!--/.row-fluid-->
			</div>
		</div><!--/.main-container-->
		<%@ include file="/WEB-INF/views/foot.jsp"%>
		<script>
		$("#btn-scroll-up").hide();
		</script>
	</body>
</html>
	