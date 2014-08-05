package com.joysdk.game.controller.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.user.proxy.UserProxy;
import com.joysdk.common.util.GenSeqUtil;
import com.joysdk.common.util.KeyStoreUtil;
import com.joysdk.common.util.RequestUtil;
import com.joysdk.common.web.context.SystemProperties;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.model.KeyStore;
import com.joysdk.game.service.KeyStoreService;

@Controller
@RequestMapping("/gameAdmin/keystore")
public class KeyStoreController {
    
    private static final Logger logger=LoggerFactory.getLogger(KeyStoreController.class);
    
    @Autowired
    private KeyStoreService keyStoreService;

    @Autowired
    private UserProxy userProxy;

    @RequestMapping("/list")
    public ModelAndView keyStoreList(HttpServletRequest request, HttpServletResponse response) throws GameException {
        OnlineUser onlineUser=userProxy.getOnlineUser(request);
        List<KeyStore> keystores=keyStoreService.getKeyStoresByCpId(onlineUser.getCpId());
        return new ModelAndView("keystore/list").addObject("keystores", keystores);
    }

    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("keystore/add");
    }

    @RequestMapping(value="/get", method=RequestMethod.GET)
    public ModelAndView getByFileId(HttpServletRequest request, HttpServletResponse response){
        String fileId=RequestUtil.getString(request, "fileId");
        KeyStore keystore=keyStoreService.getKeyStoresByFileId(fileId);
        
        return new ModelAndView("keystore/add")
                .addObject("keystore", keystore)
                .addObject("fileId", fileId)
                .addObject("errorCode", 1)
                .addObject("errorMsg", "证书创建成功");
    }    
    
    
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public ModelAndView addKeyStore(KeyStore keystore, HttpServletRequest request, HttpServletResponse response)
        throws GameException {
        String errorMsg="";
        ModelAndView mv=new ModelAndView("keystore/add");
        if(StringUtils.isBlank(keystore.getName())) {
            errorMsg="签名证书名称为空";
        } else if(StringUtils.isBlank(keystore.getAlias())) {
            errorMsg="签名证书别名为空";
        } else if(StringUtils.isBlank(keystore.getStorePass())) {
            errorMsg="证书密钥库的密码为空";
        } else if(StringUtils.isBlank(keystore.getMainPass())) {
            errorMsg="证书别名条目的密码为空";
        } else if(StringUtils.isBlank(keystore.getCommonName())) {
            errorMsg="签名证书名字与姓氏为空";
        } else if(StringUtils.isBlank(keystore.getOrgName())) {
            errorMsg="签名证书组织名称为空";
        }
        
        String fileId=GenSeqUtil.getUUID();
        try {
            KeyStoreUtil.createKeyStoreFile(fileId, keystore.getAlias(), keystore.getCommonName(), keystore.getOrgName(),
                keystore.getStorePass(), keystore.getMainPass());
            keystore.setFileId(fileId);
        } catch(Exception e) {
            logger.error("create keystore error:\n", e);
            errorMsg="签名证书别名冲突，请更换";
        }
        if(!StringUtils.isBlank(errorMsg)) {
           return mv.addObject("errorMsg", errorMsg);
        }
        OnlineUser onlineUser=userProxy.getOnlineUser(request);
        keystore.setCpId(onlineUser.getCpId());
        keyStoreService.createKeyStore(keystore);
        mv.setViewName("redirect:get?fileId="+keystore.getFileId());
        return mv;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String deleteKeyStore(@PathVariable Integer id,HttpServletRequest request, HttpServletResponse response) throws GameException {
        response.setContentType("text/html;charset=UTF-8");  
        keyStoreService.deleteKeyStoresById(id);
        return "删除成功";
    }

    @RequestMapping("/upload")
    public ModelAndView uploadKeyStore(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response) throws GameException {
        ModelAndView mv=new ModelAndView("keystore/add");
        String fileId=GenSeqUtil.getUUID();
        StringBuilder filePath=new StringBuilder(SystemProperties.getProperty("upload.keystore.dir"));
        filePath.append(fileId).append(".keystore");
        File newFile=new File(filePath.toString());
        try {
            file.transferTo(newFile);
        } catch(Exception e) {
            logger.error("upload file error:\n", e);
        } 
        mv.setViewName("redirect:get?fileId="+fileId);
        return mv;
    }

}
