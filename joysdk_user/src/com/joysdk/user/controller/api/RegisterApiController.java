package com.joysdk.user.controller.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.joysdk.common.Constants;
import com.joysdk.common.exception.JoySdkException;
import com.joysdk.common.game.model.CP;
import com.joysdk.common.game.model.Game;
import com.joysdk.common.game.proxy.GameProxy;
import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.model.Result;
import com.joysdk.common.user.model.User;
import com.joysdk.common.util.AccountUtil;
import com.joysdk.common.util.EncryptUtil;
import com.joysdk.common.util.GenSeqUtil;
import com.joysdk.common.util.RequestUtil;
import com.joysdk.common.web.context.SystemProperties;
import com.joysdk.user.exception.LoginException;
import com.joysdk.user.service.LoginService;
import com.joysdk.user.service.UserService;

@Controller
public class RegisterApiController {

    private static final Logger logger=LoggerFactory.getLogger(RegisterApiController.class);

    @Autowired
    private GameProxy gameProxy;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private LoginService loginService;

    @RequestMapping(value="register/createUser", method=RequestMethod.POST)
    public ModelAndView clientRegister(User user, HttpServletRequest request, HttpServletResponse response) throws JoySdkException {
        Result<OnlineUser> result=new Result<OnlineUser>();
        String account=RequestUtil.getString(request, "account");
        String password=RequestUtil.getString(request, "password");
        String passConfirm=RequestUtil.getString(request, "passConfirm");
        Integer referUserId=RequestUtil.getInteger(request, "referUserId");
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        String code=RequestUtil.getString(request, "code");
        CP cp=gameProxy.getCPById(cpId);
        if(cp == null){
            throw new LoginException(1000); 
        }
        if(!AccountUtil.isAvailableUsername(account)){
            throw new JoySdkException(1002);
        }
        if(!AccountUtil.isAvailablePassword(password)){
            throw new JoySdkException(1003);
        }
        if(!password.equals(passConfirm)){
            throw new JoySdkException(1005);
        }
        String ua=RequestUtil.getString(request, "ua");
        String ip=RequestUtil.getString(request, "ip");
        if(ip == null) {
            ip=RequestUtil.getUserIpAddr(request);
        }
        Integer channelId=RequestUtil.getInteger(request, Constants.CHANNEL_ID);
        Integer gameId=RequestUtil.getInteger(request, Constants.GAME_ID);
        Game game=gameProxy.getGameById(gameId);
        if(game == null){
            throw new LoginException(1001); 
        }
        if(AccountUtil.isAvailableAccount(account)){
            user.setUserName(account);
        }else{
            // 生产唯一用户名
            String tmpUserName=GenSeqUtil.getSequenceString();
            user.setUserName(tmpUserName);
            if(AccountUtil.isEmail(account)){
                user.setEmail(account);
            }else if(AccountUtil.isPhoneNum(account)){
                user.setPhone(account);
            }
            // checkcode
        }
        String encodedPassword=EncryptUtil.encoderByMd5(password + SystemProperties.getProperty("yun.pwd.salt"));
        user.setPassword(encodedPassword);
        user.setChannelId(channelId);
        user.setRegisterGameId(gameId);
        user.setRegisterIp(ip);
        user.setDateCreated(new Date());
        user.setReferUserId(referUserId);
        try {
            userService.createUser(user);
        } catch(Exception e) {
            logger.error("", e);
            throw new JoySdkException(1006);
        }
        OnlineUser onlineUser=loginService.createOnlineUser(account.trim().toLowerCase(), gameId, user);
        if(onlineUser != null){
            onlineUser.setToken(null);// 真实token 不要发送到客户端
            result.setData(onlineUser);
            result.setSuccess(true);
            request.setAttribute(Constants.TOKEN, onlineUser.getToken());
            // log
        }else{
            result.setSuccess(false);
            result.setErrorType(1004);
            result.setMessage(SystemProperties.getMsg(1004));
        }
        return new ModelAndView(Constants.JSON_VIEW, Constants.JSON_ROOT, result);
    }

    @RequestMapping(value="register/checkAccount", method=RequestMethod.POST)
    public ModelAndView checkAccount(HttpServletRequest request, HttpServletResponse response) throws JoySdkException {
        Result<Boolean> result=new Result<Boolean>();

        return new ModelAndView(Constants.JSON_VIEW, Constants.JSON_ROOT, result);
    }

}
