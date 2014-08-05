package com.joysdk.common.web.context;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joysdk.common.Constants;
import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.util.FilterUtil;

/**
 * 处理web上的访问，管理后台相关权限处理
 * @author Sunxc
 *
 */
public class SecurityFilter extends AbstractSecurityFilter {

    private final static Logger loger=LoggerFactory.getLogger(SecurityFilter.class);

    private String loginUrl="";

    private final String SPECIAL_GOOGLE_URL="favicon.ico";

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)res;
        request.setCharacterEncoding("UTF-8");
        String servletPath=request.getServletPath();
        boolean isNeedLogin=true;
        try {
            if(servletPath != null && FilterUtil.checkRequestUrl(servletPath, ignoreUrlList)) {
                chain.doFilter(req, res);
                return;
            }

            final boolean checkCookieUrlIsExist=FilterUtil.checkRequestUrl(servletPath, getAllResources());
            if(checkCookieUrlIsExist) {// 受保护的资源，如后台
                OnlineUser onlineUser=userProxy.getOnlineUser(request);
                if(onlineUser != null) {
                    isNeedLogin=false;
                }
                if(isNeedLogin) {// 未登录直接跳转
                    sendRedirectLogin(request, response);
                    return;
                }
                checkUserPermission(request, response);
            } 
        } catch(Exception e) {
            loger.error(" http request " + servletPath, e);
        }
        chain.doFilter(req, res);
    }

    private void checkUserPermission(HttpServletRequest request, HttpServletResponse response) {
        try {
         // 检查权限, 在登录的时候做判断，如果用户类型是管理用户则获取他的资源列表，放到redis中
            
        } catch(Exception e) {
            loger.error("Exception is  =======" + e);
        }

    }

    /**
     * 跳转到登录页面
     * @param request
     * @throws IOException
     */
    private void sendRedirectLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String referer=request.getHeader("referer");
        final String serverName=request.getServerName();
        final String qString=request.getQueryString();

        String backUrl=request.getRequestURL().toString();

        if("localhost".equals(serverName)) {
            if(StringUtils.isNotBlank(referer)) {
                // 将localhost 替换成域名的形式。
                String replacement=referer.substring(referer.indexOf("//") + 2);
                replacement=replacement.substring(0, replacement.indexOf("/"));
                backUrl=backUrl.replaceFirst(serverName + ":" + request.getServerPort(), replacement);
            }
        }
        loger.debug("returnUrl =======" + backUrl);
        if(StringUtils.isNotBlank(qString)) {
            backUrl+="?" + qString;
        }
        backUrl=URLEncoder.encode(backUrl, "UTF-8");
        response.sendRedirect(loginUrl + "?returnUrl=" + backUrl);
    }

    /**
     * 初始化相关参数，并加载相关的bean
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        loginUrl=SystemProperties.getProperty("passport.domain")+"/login";
    }

    @SuppressWarnings("unchecked")
    private List<String> getAllResources() {
        List<String> resources=null;
        try {
            if(servletContext != null) {
                final Object tmp=servletContext.getAttribute(Constants.APPLICATION_RESOURCES);
                if(tmp != null) {
                    resources=(List<String>)tmp;
                }
            }
        } catch(Exception e) {
            loger.error("Exception is  =======" + e);
        }

        return resources;
    }
    

}
