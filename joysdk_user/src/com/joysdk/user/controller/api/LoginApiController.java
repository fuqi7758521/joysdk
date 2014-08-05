package com.joysdk.user.controller.api;

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
import com.joysdk.common.exception.ApiJoySdkException;
import com.joysdk.common.exception.JoySdkException;
import com.joysdk.common.game.model.CP;
import com.joysdk.common.game.model.Game;
import com.joysdk.common.game.proxy.GameProxy;
import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.model.Result;
import com.joysdk.common.util.AccountUtil;
import com.joysdk.common.util.EncryptUtil;
import com.joysdk.common.util.RequestUtil;
import com.joysdk.common.web.context.SystemProperties;
import com.joysdk.user.model.UserLog;
import com.joysdk.user.service.LoginService;
import com.joysdk.user.service.UserLogService;

@Controller
@RequestMapping("/api")
public class LoginApiController {

    private static final Logger logger=LoggerFactory.getLogger(LoginApiController.class);

    @Autowired
    private GameProxy gameProxy;
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserLogService userLogService;
    
    @RequestMapping(value="login", method=RequestMethod.POST)
    public ModelAndView clientLogin(HttpServletRequest request, HttpServletResponse response) throws ApiJoySdkException, JoySdkException{
        Result<OnlineUser> result=new Result<OnlineUser>();
        String account=RequestUtil.getString(request, "account");
        String password=RequestUtil.getString(request, "password");
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        if(StringUtils.isEmpty(account) || !AccountUtil.isAvailableAccount(account)){
            throw new ApiJoySdkException(1002);
        }
        if(cpId == null || cpId.intValue() == 0){
            throw new ApiJoySdkException(1000); 
        }
        CP cp=gameProxy.getCPById(cpId);
        if(cp == null){
            throw new ApiJoySdkException(1000); 
        }
        if(StringUtils.isEmpty(password) || !AccountUtil.isAvailablePassword(password)){
            throw new ApiJoySdkException(1003); 
        }else{
            password=EncryptUtil.getUserRealPassword(password, cp.getSecretKey());
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
            throw new ApiJoySdkException(1001); 
        }
        OnlineUser onlineUser=loginService.login(account.trim().toLowerCase(), password.trim(), gameId);
        if(onlineUser != null){
            request.setAttribute(Constants.TOKEN, onlineUser.getToken());
            onlineUser.setToken(null);// 真实token 不要发送到客户端
            result.setData(onlineUser);
            result.setSuccess(true);
            UserLog log=new UserLog(onlineUser.getUserId(), game.getId(), channelId, ip, ua);
            userLogService.createUserLog(log);
        }else{
            result.setSuccess(false);
            result.setErrorType(1004);
            result.setMessage(SystemProperties.getMsg(1004));
        }
        
        return new ModelAndView(Constants.JSON_VIEW, Constants.JSON_ROOT, result);
    }

}
