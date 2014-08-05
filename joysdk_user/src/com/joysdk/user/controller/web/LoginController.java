package com.joysdk.user.controller.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.joysdk.common.Constants;
import com.joysdk.common.game.proxy.GameProxy;
import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.util.AccountUtil;
import com.joysdk.common.util.EncryptUtil;
import com.joysdk.common.util.RequestUtil;
import com.joysdk.user.exception.LoginException;
import com.joysdk.user.service.LoginService;
import com.joysdk.user.service.UserLogService;


@Controller
public class LoginController {

    private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private GameProxy gameProxy;
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserLogService userLogService;
    
    @RequestMapping(value="login", method=RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws LoginException{
        String errorMsg=null;
        ModelAndView mv=new ModelAndView("login");
        String account=RequestUtil.getString(request, "account");
        String password=RequestUtil.getString(request, "password");
        String remberMe=RequestUtil.getString(request, "remberMe");
        String returnUrl=RequestUtil.getString(request, "returnUrl");
        if(StringUtils.isEmpty(account) || !AccountUtil.isAvailableAccount(account)){
            errorMsg="帐号为空，或格式不对。";
        }else if(StringUtils.isEmpty(password) || !AccountUtil.isAvailablePassword(password)){
            errorMsg="密码为空，或格式不对。";
        }
        
        if(StringUtils.isNotBlank(errorMsg)){
            return mv.addObject("errorMsg", errorMsg);
        }
        
        OnlineUser onlineUser=loginService.login(account.trim().toLowerCase(), password, 0);
        final String cookieValue=EncryptUtil.encodeCookieValue(onlineUser.getToken(), Constants.ENCODE_KEY);
        Cookie loginCookie = new Cookie(Constants.COOKIE_USER_LOGIN_KEY, cookieValue);
        loginCookie.setDomain(Constants.COOKIE_DOMAIN);
        loginCookie.setPath(Constants.COOKIE_PATH);
        if("true".equals(remberMe)){
            loginCookie.setMaxAge(60*60*24*30*3);   //生命周期
        }
        response.addCookie(loginCookie);
        
        try {
            if(StringUtils.isNotEmpty(returnUrl)){
                response.sendRedirect(returnUrl);
                return null;
            }
        } catch(Exception e) {
            logger.error("login:", e);
        }
        return mv;
    }
    
    @RequestMapping(value="login", method=RequestMethod.GET)
    public String loginGet(HttpServletRequest request, HttpServletResponse response) throws LoginException{
        return "login";
    }
}
