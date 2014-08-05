package com.joysdk.common.acs.proxy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class AcsProxy {

    /**
     * 获取acs中存放的受保护的资源url
     * @return
     */
    public List<String> getSimpleResources(){
        List<String> list=new ArrayList<String>();
        list.add("/gameAdmin/**");
        list.add("/userAdmin/**");
        return list;
    }
}
