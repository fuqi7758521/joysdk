package com.joysdk.user.controller.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.joysdk.common.Constants;
import com.joysdk.common.game.model.CP;
import com.joysdk.common.game.proxy.GameProxy;
import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.user.model.User;
import com.joysdk.common.util.AccountUtil;
import com.joysdk.common.util.EncryptUtil;
import com.joysdk.common.util.RequestUtil;
import com.joysdk.common.web.context.SystemProperties;
import com.joysdk.user.exception.LoginException;
import com.joysdk.user.exception.UserException;
import com.joysdk.user.model.UserInfo;
import com.joysdk.user.model.UserType;
import com.joysdk.user.service.LoginService;
import com.joysdk.user.service.RegisterService;
import com.joysdk.user.service.UserService;

@Controller
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameProxy gameProxy;

    @Autowired
    private RegisterService registerService;

    @RequestMapping(value="register", method=RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value="register", method=RequestMethod.POST)
    public ModelAndView doRegister(User newUser, UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) throws UserException {
        String webUI="";
        String errorMsg="";
        ModelAndView mv=new ModelAndView("register");
        String repassword=RequestUtil.getString(request, "repassword");
        String userName=RequestUtil.getString(request, "userName");
        String qq=RequestUtil.getString(request, "qq");

        if(!AccountUtil.isAvailableAccount(newUser.getUserName())) {
            errorMsg="用户名不符合规则，请重新填写。";
            webUI="userName";
        } else if(!AccountUtil.isAvailablePassword(newUser.getPassword())) {
            errorMsg="密码不符合规则，请重新填写。";
            webUI="password";
        } else if(!newUser.getPassword().equals(repassword)) {
            errorMsg="重复密码与原密码不一致。";
            webUI="repassword";
        } else if(!AccountUtil.isEmail(newUser.getEmail())) {
            errorMsg="请输入合法邮箱。";
            webUI="email";
        } else if(!AccountUtil.isPhoneNum(newUser.getPhone())) {
            errorMsg="请输入合法手机号码。";
            webUI="phone";
        } else if(StringUtils.length(qq) < 5 || StringUtils.length(qq) > 15 || !StringUtils.isNumeric(qq)) {
            errorMsg="请输入合法QQ号码。";
            webUI="qq";
        }

        if(userService.checkAccount(newUser.getUserName()) != null) {
            errorMsg="您输入的用户名被占用，请重新输入。";
            webUI="userName";
        } else if(userService.checkAccount(newUser.getEmail()) != null) {
            errorMsg="您输入的邮箱被占用，请重新输入。";
            webUI="email";
        } else if(userService.checkAccount(newUser.getPhone()) != null) {
            errorMsg="您输入的手机号被占用，请重新输入。";
            webUI="phone";
        }

        CP cp=new CP();
        cp.setSecretKey(EncryptUtil.getPass(10));
        cp.setNotifyKey(EncryptUtil.getPass(10));
        cp=gameProxy.createOrUpdateCP(cp);
        if(cp.getId()==0){
            errorMsg="系统异常，请稍后再试";
        }
        
        if(StringUtils.isNotBlank(errorMsg)) {
            return mv.addObject("errorMsg", errorMsg)
                     .addObject("repassword", repassword)
                     .addObject("webUI", webUI);
        }

        // 验证输入参数合法后，没有错误信息
        String encodedPassword=EncryptUtil.encoderByMd5(newUser.getPassword() + SystemProperties.getProperty("yun.pwd.salt"));
        newUser.setPassword(encodedPassword);
        String ip=RequestUtil.getUserIpAddr(request);
        newUser.setUserType(UserType.CP_USER.getType());
        newUser.setRegisterIp(ip);

        userInfo.setQq(qq);
        
        newUser.setCpId(cp.getId());
        registerService.createUser4CP(newUser, userInfo);
        
        try {
            OnlineUser onlineUser=loginService.createOnlineUser(userName.trim().toLowerCase(), 0, newUser);// 注册游戏id为零
            
            final String cookieValue=EncryptUtil.encodeCookieValue(onlineUser.getToken(), Constants.ENCODE_KEY);
            Cookie loginCookie = new Cookie(Constants.COOKIE_USER_LOGIN_KEY, cookieValue);
            loginCookie.setDomain(Constants.COOKIE_DOMAIN);
            loginCookie.setPath(Constants.COOKIE_PATH);
            response.addCookie(loginCookie);
        } catch(LoginException e) {
            e.printStackTrace();
        }
        
        return mv.addObject("errorCode", 1).addObject("errorMsg", "恭喜，注册成功，返回登录页面");
    }
}
