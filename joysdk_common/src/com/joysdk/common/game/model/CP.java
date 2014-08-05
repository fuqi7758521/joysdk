package com.joysdk.common.game.model;

import java.io.Serializable;
import java.util.Date;


public class CP implements Serializable{

    private static final long serialVersionUID=1L;

    private int id;
    
    private String name="";
    
    private String secretKey;
    
    private String notifyKey;
    
    private int status;
    
    private Date dtCreateTime;

    
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

    
    public String getSecretKey() {
        return secretKey;
    }

    
    public void setSecretKey(String secretKey) {
        this.secretKey=secretKey;
    }

    
    public String getNotifyKey() {
        return notifyKey;
    }

    
    public void setNotifyKey(String notifyKey) {
        this.notifyKey=notifyKey;
    }

    
    public int getStatus() {
        return status;
    }

    
    public void setStatus(int status) {
        this.status=status;
    }

    
    public Date getDtCreateTime() {
        return dtCreateTime;
    }

    
    public void setDtCreateTime(Date dtCreateTime) {
        this.dtCreateTime=dtCreateTime;
    }
    
    
}
