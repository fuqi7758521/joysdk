package com.joysdk.game.model;

import java.io.Serializable;
import java.util.List;


public class Channel implements Serializable{

    private static final long serialVersionUID=1L;

    private int id;
    
    private String name;
    
    private int status;
    
    private int pid;
    
    private int cpId;
    
    private List<ChannelPropery> proNames;

    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id=id;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name=name;
    }

    
    public int getStatus() {
        return status;
    }

    
    public void setStatus(int status) {
        this.status=status;
    }

    
    public int getPid() {
        return pid;
    }

    
    public void setPid(int pid) {
        this.pid=pid;
    }


    
    public List<ChannelPropery> getProNames() {
        return proNames;
    }


    
    public void setProNames(List<ChannelPropery> proNames) {
        this.proNames=proNames;
    }

    
    public int getCpId() {
        return cpId;
    }
    
    public void setCpId(int cpId) {
        this.cpId=cpId;
    }
    
    
}
