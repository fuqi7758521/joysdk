package com.joysdk.common.web.context;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joysdk.common.Constants;
import com.joysdk.common.exception.AuthenticationException;
import com.joysdk.common.game.model.CP;
import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.util.EncryptUtil;
import com.joysdk.common.util.FilterUtil;
import com.joysdk.common.util.ParameterUtil;
import com.joysdk.common.util.RequestUtil;


public class SecurityApiFilter extends AbstractSecurityFilter {

    private final static Logger loger=LoggerFactory.getLogger(SecurityApiFilter.class);
    
    @Override
    public void destroy() {
        super.destroy();
    }
    
    /**
     * 初始化相关参数，并加载相关的bean
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)req;
        request.setCharacterEncoding("UTF-8");
        String servletPath=request.getServletPath();
        
        try {
            final boolean ignoreUrlIsExist=FilterUtil.checkRequestUrl(servletPath, ignoreUrlList);
            if(!ignoreUrlIsExist) {
                String token=request.getHeader("token");
                checkSession(request, token);
                checkRequest(request);
            }
        } catch(Exception e) {
            loger.error(" http request " + servletPath, e);
        }

        
        chain.doFilter(req, res);
    }

    /**
     * 检查api请求的参数验证
     * @param request
     * @throws AuthenticationException
     */
    private void checkRequest(HttpServletRequest request) throws AuthenticationException {
        String sign=RequestUtil.getString(request, Constants.SIGN_PARAM_NAME);
        CP cp=null;
        if(StringUtils.isEmpty(sign)) {
            throw new AuthenticationException(9002);
        }
        Integer gameId=RequestUtil.getInteger(request, Constants.GAME_ID);
        if(gameId == null || gameId.intValue() == 0) {
            throw new AuthenticationException(9002);
        }
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        if(cpId == null || cpId.intValue() == 0) {
            throw new AuthenticationException(9003);
        } else {
            cp=gameProxy.getCPById(cpId);
        }
        if(cp == null) {
            throw new AuthenticationException(9003);
        }

        Map<String, String[]> params=new HashMap<String, String[]>(request.getParameterMap());
        String baseString=ParameterUtil.getSignData(params);
        String temSign=EncryptUtil.encoderByMd5(baseString + cp.getSecretKey());
        if(!sign.equals(temSign)) {
            throw new AuthenticationException(9002);
        }
    }
    
    /**
     * 检查请求用户是否在线
     * @param request
     * @return
     * @throws AuthenticationException
     */
    private OnlineUser checkSession(HttpServletRequest request, String token) throws AuthenticationException {
        OnlineUser onlineUser=null;
        Date loginTime=null;
        try {
            loginTime=EncryptUtil.getLastActTime(token);
        } catch(Exception e) {
            throw new AuthenticationException(10000);
        }
        long minites=(System.currentTimeMillis() - loginTime.getTime()) / (60 * 1000);
        if(minites >= 20) { // 如果大于20分钟
            throw new AuthenticationException(9001);
        }
        String decToken=EncryptUtil.decipheredTicket(token);
        Integer gameId=RequestUtil.getInteger(request, Constants.GAME_ID);
        onlineUser=userProxy.getOnlineUser(decToken, gameId);
        if(onlineUser == null) {
            throw new AuthenticationException(9001);
        } else {
            request.setAttribute(Constants.ONLINE_USER, onlineUser);;
        }
        return onlineUser;
    }

}
