package com.joysdk.game.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.joysdk.common.game.model.Game;
import com.joysdk.common.game.proxy.GameProxy;
import com.joysdk.common.model.OnlineUser;
import com.joysdk.common.user.proxy.UserProxy;
import com.joysdk.common.util.RequestUtil;
import com.joysdk.game.exception.GameException;
import com.joysdk.game.service.CPService;
import com.joysdk.game.service.GameService;

@Controller
@RequestMapping("/gameAdmin/game")
public class GameController {

    @Autowired
    private GameService gameService;
    
    @Autowired
    private CPService cPService;
    
    @Autowired
    private GameProxy gameProxy;
    
    @Autowired
    private UserProxy userProxy;
    
    @RequestMapping("/list")
    public ModelAndView gameList(HttpServletRequest request,HttpServletResponse response) throws GameException{
        OnlineUser onlineUser=userProxy.getOnlineUser(request);
        
        List<Game> gameList=gameService.getGamesByCpId(onlineUser.getCpId());

        return new ModelAndView("game/games")
                .addObject("gameList", gameList);
    }
    
    @RequestMapping(value={"/edit/{gameId}"}, method=RequestMethod.GET)
    public ModelAndView goEditPage(@PathVariable Integer gameId, HttpServletRequest request,HttpServletResponse response) throws GameException{
        Game game=gameService.getGameById(gameId);
        return new ModelAndView("game/editGame").addObject("game", game);
    }
    
    @RequestMapping(value={"/add"}, method=RequestMethod.GET)
    public String addNewGamePage(){
        return "game/editGame";
    }
    
    @RequestMapping(value="/save", method=RequestMethod.POST)
    @ResponseBody
    public ModelAndView doSaveGame(Game game, HttpServletRequest request,HttpServletResponse response) throws GameException{
        ModelAndView mv=new ModelAndView("game/editGame");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        String alias=RequestUtil.getString(request, "alias");
        String name=RequestUtil.getString(request, "name");
        String packageName=RequestUtil.getString(request, "packageName");
        String version=RequestUtil.getString(request, "version");
        String coin=RequestUtil.getString(request, "coin");
        Integer coinRatio=RequestUtil.getInteger(request, "coinRatio");

        if(StringUtils.isBlank(name)){
            return mv.addObject("errorMsg", "游戏名称未空");
        }
        
        if(StringUtils.isBlank(alias)){
            return mv.addObject("errorMsg", "游戏别名为空，请重新填写。");
        }
        
        if(StringUtils.isNotBlank(alias) && (gameId==null||gameId==0)){
            boolean isNotExist=gameService.checkGameAlias(alias);
            if(!isNotExist){
                return mv.addObject("errorMsg", "游戏别名被占用，请更换。");
            }
        }
        
        if(StringUtils.isBlank(packageName)){
            return mv.addObject("errorMsg", "游戏名称为空");
        }
        if(StringUtils.isBlank(version)){
            return mv.addObject("errorMsg", "游戏版本号为空");
        }
        if(StringUtils.isBlank(coin)){
            return mv.addObject("errorMsg", "游戏币名称为空");
        }
        if(coinRatio == null || coinRatio.intValue() == 0){
            return mv.addObject("errorMsg", "请正确填写游戏币兑换比例");
        }
        
        // 验证成功, 保存数据
        OnlineUser onlineUser=userProxy.getOnlineUser(request);
        
        if(gameId != null && gameId.intValue() != 0){// 如果gameid 存在则修改，否则添加
            Game gameOld=gameService.getGameById(gameId);
            game.setId(gameOld.getId());
            game.setSeqNum(gameOld.getSeqNum());
            game.setDtCreateTime(gameOld.getDtCreateTime());
            game.setCpId(gameOld.getCpId());
        }else{
            List<Game> gameList=gameService.getGamesByCpId(onlineUser.getCpId());
            game.setSeqNum(gameList.size()+1);
        }
        
        gameService.createOrUpdateGame(game);
        return mv.addObject("errorCode", 1)
                 .addObject("errorMsg", "保存成功")
                 .addObject("gameId", game.getId());
    }
    
}
