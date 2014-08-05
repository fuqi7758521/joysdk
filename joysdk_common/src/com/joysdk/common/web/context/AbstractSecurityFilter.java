package com.joysdk.common.web.context;

import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joysdk.common.Constants;
import com.joysdk.common.exception.AuthenticationException;
import com.joysdk.common.game.proxy.GameProxy;
import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.user.proxy.UserProxy;
import com.joysdk.common.util.EncryptUtil;
import com.joysdk.common.util.FilterUtil;

public abstract class AbstractSecurityFilter implements Filter{

    private final static Logger loger=LoggerFactory.getLogger(AbstractSecurityFilter.class);
    
    protected GameProxy gameProxy;

    protected UserProxy userProxy;

    private String ignoreUrl=null;

    protected List<String> ignoreUrlList;

    protected ServletContext servletContext;

    @Override
    public void destroy() {
        gameProxy=null;
        userProxy=null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext=filterConfig.getServletContext();
        String param=filterConfig.getInitParameter("trustUrlPattern");
        if(StringUtils.isNotBlank(param)) {
            ignoreUrl=param;
        }
        ignoreUrlList=FilterUtil.spliteUrlPatterns(ignoreUrl);

        userProxy=(UserProxy)GlobeContext.getApplicationContext().getBean("userProxy");
        gameProxy=(GameProxy)GlobeContext.getApplicationContext().getBean("gameProxy");
        
        if(userProxy == null || gameProxy == null){
            throw new ServletException("bean init failed");
        }
        loger.info("SecurityFilter init success");
    }
    

}
