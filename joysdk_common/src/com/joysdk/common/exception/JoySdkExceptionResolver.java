package com.joysdk.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.joysdk.common.model.Result;
import com.joysdk.common.util.JsonUtil;
import com.joysdk.common.web.context.SystemProperties;


public class JoySdkExceptionResolver implements HandlerExceptionResolver {
    
    private static final Logger logger=LoggerFactory.getLogger(JoySdkExceptionResolver.class);
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("handlerException:", ex);
        String responseMessage=null;
        PrintWriter out=null;
        if(response.isCommitted()) {
            return null;
        }
        
        if(ex instanceof ApiJoySdkException){
            String msg=ex.getMessage();
            if(StringUtils.isNotBlank(msg) && StringUtils.isNumeric(msg)){
                Result<Object> res=new Result<Object>();
                int errType=Integer.parseInt(msg);
                res.setErrorType(errType);
                res.setMessage(SystemProperties.getMsg(errType));
                
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                response.setHeader("Cache-Control", "no-cache");
                responseMessage=JsonUtil.Object2Json(res);
                try {
                    out=response.getWriter();
                    out.print(responseMessage);
                    out.flush();
                } catch(IOException e) {
                } finally {
                    if(out != null) {
                        out.close();
                        out=null;
                    }
                }
            }
        }else{
            ex.printStackTrace(out);
        }
        return null;
    }

}
