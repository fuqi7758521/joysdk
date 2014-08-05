package com.joysdk.game.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.model.Paging;
import com.joysdk.common.user.proxy.UserProxy;
import com.joysdk.common.util.RequestUtil;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.model.Channel;
import com.joysdk.game.model.ChannelPropery;
import com.joysdk.game.service.ChannelService;

@Controller
@RequestMapping("/gameAdmin/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;
    
    @Autowired
    private UserProxy userProxy;
    
    /**
     * 系统管理员能够访问的渠道列表
     * @param request
     * @param response
     * @return
     * @throws GameException
     */
    @RequestMapping("/list")
    public ModelAndView channelList(HttpServletRequest request,HttpServletResponse response) throws GameException{
        Integer pid=RequestUtil.getInteger(request, "pid");
        Integer start=RequestUtil.getInteger(request, "start");
        OnlineUser onlineUser=userProxy.getOnlineUser(request);

        onlineUser.setCpId(0);// 测试的时候方便 设置cpid=0
        
        Paging<Channel> paging=new Paging<Channel>();
        paging.setStartNum(start==null?1:start);
        Paging<Channel> channels=channelService.getChannels(pid==null?0:pid, onlineUser.getCpId(), paging);
        
        return new ModelAndView("channel/channels")
                    .addObject("channels", channels);
    }
    
    @RequestMapping(value="/edit/{channelId}", method=RequestMethod.GET)
    public ModelAndView channelEdit(@PathVariable Integer channelId, HttpServletRequest request,HttpServletResponse response) throws GameException{
        Channel channel=channelService.getChannelById(channelId);
        return new ModelAndView("channel/editChannel").addObject("channel", channel);
    }
    
    @RequestMapping("/add")
    public ModelAndView channeladd(HttpServletRequest request,HttpServletResponse response) throws GameException{
        return new ModelAndView("channel/editChannel");
    }
    
    @RequestMapping("/save")
    public ModelAndView channelSave(Channel channel, HttpServletRequest request,HttpServletResponse response) throws GameException{
        ModelAndView mv=new ModelAndView("channel/editChannel");
        
        Integer id=RequestUtil.getInteger(request, "channelId");
        String name=RequestUtil.getString(request, "name");
        String proNameStr=RequestUtil.getString(request, "proNameStr");
        
        if(StringUtils.isEmpty(name)){
            return mv.addObject("errorMsg", "请填写渠道名称"); 
        }else if(StringUtils.isEmpty(proNameStr)){
            return mv.addObject("errorMsg", "请填写渠道属性"); 
        }
        String[] proArr=proNameStr.split(",");
        List<ChannelPropery> pros=new ArrayList<ChannelPropery>(proArr.length);
        
        if(id != null && id.intValue() != 0){
            channelService.updateChannel(id, channel.getName().trim());
        }else{
            channelService.createChannel(channel);
            id=channel.getId();
        }
        for(String pro : proArr){
            String[] nameAndId=pro.split("\\|");
            ChannelPropery channelPro=new ChannelPropery();
            String proName=nameAndId[0].trim();
            channelPro.setProperyName(nameAndId[0]);
            if(nameAndId.length>1){
                int proId=Integer.parseInt(nameAndId[1].trim());
                channelPro.setId(proId);
                channelService.updateChannelPropery(proId, proName);
            }else{
                channelPro.setChannelId(id);
                channelService.createChannelPropery(channelPro);
            }
            pros.add(channelPro);
        }
        channel.setProNames(pros);

        mv=new ModelAndView("redirect:edit/"+id);
        return mv;
    }
}
