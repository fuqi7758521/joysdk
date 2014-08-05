package com.joysdk.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    
    private final static String CHAR_SET="UTF-8";
    
    public static String httpGet(String httpUrl, String params){
        return httpGet(httpUrl, params, CHAR_SET);
    }
    
    public static String httpPost(String httpUrl, String params){
        return httpPost(httpUrl, params, CHAR_SET);
    }
    
    public static String httpGet(String httpUrl, String params, String charSet){
        try {
            if(null != params && params.length() > 0) {
                if(httpUrl.indexOf("?") == -1) {
                    httpUrl+="?" + params;
                } else {
                    httpUrl+="&" + params;
                }
            }

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(httpUrl);
            HttpResponse response = client.execute(request);
            return processEntity(response.getEntity());
        } catch(Exception e) {
            logger.error("http request error:"+e);
        }
        return null;
    }
    
    public static String httpPost(String httpUrl, String params, String charSet){
        
        try {
            CloseableHttpClient client = HttpClients.createDefault();  
            HttpPost post = new HttpPost(httpUrl);
            StringEntity entity=new StringEntity(params, charSet);
            entity.setContentType("application/x-www-form-urlencoded");
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            return processEntity(response.getEntity());
        } catch(Exception e) {
            logger.error("http request error:"+e);
        }
        return null;
    }
    
    /**
     * 读取返回结果
     * @param entity
     * @param statusCode
     * @throws IllegalStateException
     * @throws IOException
     */
    private static String processEntity(HttpEntity entity) throws IllegalStateException, IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(entity.getContent()));
        String line, result="";
        StringBuilder sBuilder=new StringBuilder(result);
        while((line=br.readLine()) != null) {
            sBuilder.append(line);
        }
        result=sBuilder.toString();
        logger.debug(result);
        br.close();
        return result;
    }
    
    public static void main(String[] args) {
        String s=httpPost("http://localhost:8080/user/api/login", "account=sunxian99&password=123123");
        System.out.println(s);
    }
    
}
