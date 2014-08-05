package com.joysdk.test;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.joysdk.common.exception.RemoteException;
import com.joysdk.common.game.model.CP;
import com.joysdk.common.game.model.Game;
import com.joysdk.common.game.service.remote.GameServiceRemote;
import com.joysdk.common.util.EncryptUtil;


public class HessionTest {

    public static void main(String[] args) throws RemoteException {
        // 服务器访问路径  
        String url = "http://localhost:8081/game/remoting/gameService";  
        HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();  
        try {  
            GameServiceRemote iRemote=(GameServiceRemote)hessianProxyFactory.create(GameServiceRemote.class, url);
            // 客户端调服务器的接口实现  
            CP cp2=iRemote.getCPById(231);
            
            System.out.println(cp2.getId());
            
            CP cp=new CP();
            cp.setSecretKey(EncryptUtil.getPass(10));
            cp.setNotifyKey(EncryptUtil.getPass(10));
            iRemote.createOrUpdateCP(cp);
            
            Game game=iRemote.getGameById(1);
            System.out.println(game.getAlias());
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        }  
    }
}
