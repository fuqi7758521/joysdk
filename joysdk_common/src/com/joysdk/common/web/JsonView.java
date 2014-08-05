package com.joysdk.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.joysdk.common.Constants;
import com.joysdk.common.model.Result;
import com.joysdk.common.util.EncryptUtil;
import com.joysdk.common.util.JsonUtil;


public class JsonView extends AbstractView{

    private static final String JSON_TYPE="application/json; charset=UTF-8";
    
    public String getContentType() {
        return JSON_TYPE;
    }


    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out=null;
        try {
            out=response.getWriter();
            Result<?> rs=(Result<?>)map.get(Constants.JSON_ROOT);
            String token=(String)request.getAttribute(Constants.TOKEN);
            if(StringUtils.isNotBlank(token)){
                String encryToken=EncryptUtil.encryptedTicket(token);
                rs.setToken(encryToken);
                response.setHeader(Constants.TOKEN, encryToken);
            }
            out.print(JsonUtil.Object2Json(rs));
            out.flush();
        } catch(IOException e) {
        } finally {
            if(out != null) {
                out.close();
                out=null;
            }
        }
    }
    
   
}
