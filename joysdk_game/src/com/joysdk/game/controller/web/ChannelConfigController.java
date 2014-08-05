package com.joysdk.game.controller.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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

import com.joysdk.common.model.Result;
import com.joysdk.common.user.proxy.UserProxy;
import com.joysdk.common.util.DateUtil;
import com.joysdk.common.util.RequestUtil;
import com.joysdk.common.web.context.SystemProperties;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.model.Channel;
import com.joysdk.game.model.ChannelConfig;
import com.joysdk.game.model.ChannelPropery;
import com.joysdk.game.model.GameSDKConfig;
import com.joysdk.game.service.ChannelConfigService;
import com.joysdk.game.service.ChannelService;

@Controller
@RequestMapping("/myChannel")
public class ChannelConfigController {

    private static final Logger logger=LoggerFactory.getLogger(KeyStoreController.class);

    @Autowired
    private ChannelConfigService channelConfigService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserProxy userProxy;

    @RequestMapping("{gameId}/{channelId}")
    public void showChannelConfig(@PathVariable Integer gameId, @PathVariable Integer channelId, HttpServletRequest request,
        HttpServletResponse response) throws GameException {
        ChannelConfig config=channelConfigService.getChannelConfigByGameId(gameId, channelId);
        System.out.println(config.getSdkConfigs().size());
    }

    /**
     * 添加渠道信息页面
     * @param gameId
     * @param request
     * @param response
     * @return
     * @throws GameException
     */
    @RequestMapping("setting/{gameId}")
    public ModelAndView addChannelConfig(@PathVariable Integer gameId, HttpServletRequest request, HttpServletResponse response)
        throws GameException {
        List<ChannelPropery> properys=null;
        ModelAndView mv=new ModelAndView("mygame/addChannel");
        Integer channelId=RequestUtil.getInteger(request, "channelId");

        List<Channel> allChannels=channelService.getAllChannels();
        if(channelId!=null && channelId.intValue()!=0){
            ChannelConfig channelConfig=channelConfigService.getChannelConfigByGameId(gameId, channelId);
            if(channelConfig == null){
                properys=channelService.getProperysByChannelId(channelId);
            }else{
                properys=new ArrayList<ChannelPropery>(channelConfig.getSdkConfigs().size());
                for(GameSDKConfig config : channelConfig.getSdkConfigs()){
                    ChannelPropery pro=config.getChannelPropery();
                    properys.add(pro);
                }
            }
            mv.addObject("channelConfig", channelConfig);
            mv.addObject("properys", properys);
        }
        
        return mv.addObject("allChannels", allChannels)
                    .addObject("gameId", gameId);
    }

    /**
     * ajax根据渠道id来选择渠道属性列表
     * @param channelId
     * @param request
     * @param response
     * @return
     * @throws GameException
     */
    @RequestMapping(value="showSetting/{gameId}-{channelId}", method=RequestMethod.GET)
    public @ResponseBody
    Result<Map<String, Object>> getChannelPropery(@PathVariable Integer gameId, @PathVariable Integer channelId, HttpServletRequest request,
        HttpServletResponse response) throws GameException {
        Result<Map<String, Object>> rs=new Result<Map<String, Object>>();
        Map<String, Object> data=new HashMap<String, Object>();
        List<ChannelPropery> properys=null;
        
        ChannelConfig channelConfig=channelConfigService.getChannelConfigByGameId(gameId, channelId);
        if(channelConfig == null){
            properys=channelService.getProperysByChannelId(channelId);
        }else{
            properys=new ArrayList<ChannelPropery>(channelConfig.getSdkConfigs().size());
            for(GameSDKConfig config : channelConfig.getSdkConfigs()){
                ChannelPropery pro=config.getChannelPropery();
                properys.add(pro);
            }
        }
        data.put("channelConfig", channelConfig);
        data.put("properys", properys);
        
        rs.setSuccess(Boolean.TRUE);
        rs.setData(data);
        return rs;
    }

    /**
     * cp用来保存某个渠道的具体信息 先保存ChannelConfig，然后获取他的id，保存GameSDKConfig
     * @param fileIcon
     * @param fileSplash
     * @param request
     * @param response
     * @return
     * @throws GameException
     */
    @RequestMapping(value="channelConfig/save", method=RequestMethod.POST)
    public ModelAndView saveChannelConfig(@RequestParam(value="fileIcon", required=false) CommonsMultipartFile fileIcon,
        @RequestParam(value="fileSplash", required=false) CommonsMultipartFile fileSplash, HttpServletRequest request,
        HttpServletResponse response) throws GameException {

        String fileName=null;
        ChannelConfig channelConfig=new ChannelConfig();
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        Integer channelId=RequestUtil.getInteger(request, "channelId");
        String properyIds=RequestUtil.getString(request, "properyIds");
        String packageName=RequestUtil.getString(request, "packageName");
        Integer channelConfigId=RequestUtil.getInteger(request, "channelConfigId");

        if(fileIcon != null && fileIcon.getSize() != 0l) {
            fileName=transferFile(fileIcon, gameId, channelId);
            channelConfig.setIcon(fileName);
        }
        if(fileSplash !=null && fileSplash.getSize() != 0l) {
            fileName=transferFile(fileSplash, gameId, channelId);
            channelConfig.setSplashPic(fileName);
        }
        channelConfig.setPackageName(packageName);
        if(channelConfigId != null && channelConfigId.intValue() != 0){
            channelConfig.setId(channelConfigId);
        }

        List<GameSDKConfig> gameConfigs=null;
        if(StringUtils.isNotEmpty(properyIds)) {
            String[] ids=properyIds.split("\\|");
            gameConfigs=new ArrayList<GameSDKConfig>(ids.length);
            for(int i=0; i < ids.length; i++) {
                String channelPropery=RequestUtil.getString(request, "channelPropery_" + ids[i]);
                GameSDKConfig config=new GameSDKConfig();
                config.setChannelProValue(channelPropery);
                config.setChannelPropery(new ChannelPropery(Integer.parseInt(ids[i])));
                gameConfigs.add(config);
            }
        }
        channelConfigService.insertOrUpdateChannelConfig(channelConfig, gameConfigs);
        return new ModelAndView("redirect:/myChannel/setting/"+gameId+"?channelId="+channelId);
    }

    private String transferFile(CommonsMultipartFile file, Integer gameId, Integer channelId) throws GameException {
        String originalName=file.getOriginalFilename();
        String postfix=originalName.substring(originalName.lastIndexOf(".") - 1);
        String currentTime=DateUtil.getCurrDate(DateUtil.FORMAT_FOUR);

        String uploadPicPath=SystemProperties.getProperty("upload.pic.dir");
        StringBuilder newName=
            new StringBuilder(currentTime).append("_").append(gameId).append("_").append(channelId).append(postfix);
        try {
            FileUtils.forceMkdir(new File(uploadPicPath));
            File filePath=new File(uploadPicPath + newName.toString());
            file.transferTo(filePath);
        } catch(Exception e) {
            logger.error("upload icon file error:\n", e);
            throw new GameException();
        }
        return newName.toString();
    }
}
