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
						<div class="row-fluid">
						<div class="position-relative">
						<div id="signup-box" class="signup-box widget-box visible no-border">
							<div class="widget-body">
								<div class="widget-main">
									<h4 class="header green lighter bigger">
										<i class="icon-group blue"></i>
										<span>新用户注册</span>
									</h4>
									<div class="space-6"></div>
									<form class="form-horizontal" id="register-form" method="post" action="${passportDomain}/register">
										<fieldset>
											<label>
											<span class="block input-icon input-icon-right">
												<input type="text" name="userName" id="userName" value="${user.userName}" class="span12" placeholder="6~32位字符，支持英文、数字和_格式" />
												<i class="icon-user"></i>
											</span>
											</label>
											<label>
												<span class="block input-icon input-icon-right">
													<input type="password" name="password" id="password" value="${user.password}" class="span12" placeholder="6-32位英文、数字、符号任意字符组合" />
													<i class="icon-lock"></i>
												</span>
											</label>
											<label>
												<span class="block input-icon input-icon-right">
													<input type="password" name="repassword" id="repassword" value="${repassword}" class="span12" placeholder="Repeat password" />
													<i class="icon-retweet"></i>
												</span>
											</label>
											<label>
											<span class="block input-icon input-icon-right">
												<input type="email" name="email" id="email" value="${user.email}" class="span12" placeholder="常用邮箱，方便密码找回" />
												<i class="icon-envelope"></i>
											</span>
											</label>
											<label>
												<span class="block input-icon input-icon-right">
													<input type="text"  name="phone" id="phone" value="${user.phone}" class="span12" placeholder="请填写手机号码" />
													<i class="icon-phone"></i>
												</span>
											</label>
											<label>
												<span class="block input-icon input-icon-right">
													<input type="text"  name="qq" id="qq" value="${userInfo.qq}"  class="span12" placeholder="QQ号码方便联系" />
													<i class="icon-comment"></i>
												</span>
											</label>
											<label>
												<input type="checkbox" name="agreementCheckbox" id="agreementCheckbox" checked="checked"/>
												<span class="lbl">
													我已阅读并同意 
													<a href="#">卓越用户协议</a>
												</span>
											</label>
											<div class="space-8"></div>
											<div class="alert <c:if test="${errorCode=='1'}">alert-success</c:if><c:if test="${errorCode!='1'}">alert-error</c:if> <c:if test="${empty errorMsg}"> hide</c:if>">
												<button type="button" class="close" data-dismiss="alert">
													<i class="icon-remove"></i>
												</button>
												<span>${errorMsg}</span>
											</div>
											<div class="clearfix">
												<button type="reset" class="width-30 pull-left btn btn-small">
													<i class="icon-refresh"></i>重置
												</button>
												<button type="submit" class="width-65 pull-right btn btn-small btn-success">注册
													<i class="icon-arrow-right icon-on-right"></i>
												</button>
											</div>
										</fieldset>
									</form>
								</div>

								<div class="toolbar center">
									<a href="${passportDomain}/login" class="back-to-login-link">
										<i class="icon-arrow-left"></i>返回登录
									</a>
								</div>
							</div><!--/widget-body-->
						</div><!--/signup-box-->
						</div>
						</div>
						</div><!--/position-relative-->
					</div>
				</div>
			</div><!--/.span-->
		</div><!--/.row-fluid-->
		<%@ include file="/WEB-INF/views/foot.jsp"%>
		<script>
			$(function(){
				if(""!="${webUI}"){
					$("#${webUI}").focus();
				}
				$("#register-form").submit(function(){
					var errorMsg="";
					if(!/^[A-Za-z]\w{5,31}$/.test($("#userName").val())){
						errorMsg="用户名不符合规则，请重新填写。";
						$("#userName").focus();
					}else if(!/.{5,31}/.test($("#password").val())){
						errorMsg="密码不符合规则，请重新填写。";
						$("#password").focus();
					}else if($("#repassword").val()!=$("#password").val()){
						errorMsg="重复密码与原密码不一致。";
						$("#repassword").focus();
					}else if(!/^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/.test($("#email").val())){
						errorMsg="请输入合法邮箱。";
						$("#email").focus();
					}else if(!/^1[3|4|5|7|8][0-9]\d{8}$/.test($("#phone").val())){
						errorMsg="请输入合法手机号码。";
						$("#phone").focus();
					}else if(!/\d{5,15}/.test($("#qq").val())){
						errorMsg="请输入qq号码方便工作人员联系。";
						$("#qq").focus();
					}else if(!$("#agreementCheckbox").is(':checked')){
						errorMsg="请阅读用户协议";
					}
					if(errorMsg != ""){
						$(".alert").removeClass("hide");
						$(".alert span").html(errorMsg);
						return false;
					}
				});	
			});
		</script>
	</body>
</html>
	