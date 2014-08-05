package com.joysdk.common.game.model;

import java.io.Serializable;
import java.util.Date;


public class Game implements Serializable{

    private static final long serialVersionUID=1L;

    private int id;
    
    private String name;
    
    private String alias;
    
    private String packageName;
    
    private String version;
    
    private int cpId;
    
    private int seqNum;
    
    private String coin;
    
    private int coinRatio;
    
    private int status;
    
    private String website;
    
    private Date dtCreateTime;

    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id=id;
    }

    public String getWebsite() {
        return website;
    }


    
    public void setWebsite(String website) {
        this.website=website;
    }


    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name=name;
    }

    
    public String getAlias() {
        return alias;
    }

    
    public void setAlias(String alias) {
        this.alias=alias;
    }

    
    
    public String getPackageName() {
        return packageName;
    }


    
    public void setPackageName(String packageName) {
        this.packageName=packageName;
    }


    
    public String getVersion() {
        return version;
    }


    
    public void setVersion(String version) {
        this.version=version;
    }


    public int getCpId() {
        return cpId;
    }

    
    public void setCpId(int cpId) {
        this.cpId=cpId;
    }

    
    public int getSeqNum() {
        return seqNum;
    }

    
    public void setSeqNum(int seqNum) {
        this.seqNum=seqNum;
    }

    
    public String getCoin() {
        return coin;
    }

    
    public void setCoin(String coin) {
        this.coin=coin;
    }

    
    public int getCoinRatio() {
        return coinRatio;
    }

    
    public void setCoinRatio(int coinRatio) {
        this.coinRatio=coinRatio;
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
