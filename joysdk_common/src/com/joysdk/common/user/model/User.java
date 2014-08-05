package com.joysdk.common.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class User implements Serializable{

    private static final long serialVersionUID=1L;

    private int id;
    
    private String userName;
    
    private String password;
    
    private String email;
    
    private String phone;
    
    private int channelId;
    
    private String channelName;
    
    private String registerIp;
    
    private String remark="";
    
    private int status;

    private Date dateCreated;
    
    private int referUserId;
    
    private int userType;
    
    private int registerGameId;
    
    private String registerGameName;
    
    private List<String> resources;
    
    private int cpId;
    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id=id;
    }

    
    public String getUserName() {
        return userName;
    }

    
    public void setUserName(String userName) {
        this.userName=userName;
    }

    
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password=password;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email=email;
    }

    
    public String getPhone() {
        return phone;
    }

    
    public void setPhone(String phone) {
        this.phone=phone;
    }

    
    public int getChannelId() {
        return channelId;
    }

    
    public void setChannelId(int channelId) {
        this.channelId=channelId;
    }

    
    public String getChannelName() {
        return channelName;
    }

    
    public void setChannelName(String channelName) {
        this.channelName=channelName;
    }

    
    public String getRegisterIp() {
        return registerIp;
    }

    
    public void setRegisterIp(String registerIp) {
        this.registerIp=registerIp;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark=remark;
    }

    
    public int getStatus() {
        return status;
    }

    
    public void setStatus(int status) {
        this.status=status;
    }

    
    public Date getDateCreated() {
        return dateCreated;
    }

    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated=dateCreated;
    }

    
    public int getReferUserId() {
        return referUserId;
    }

    
    public void setReferUserId(int referUserId) {
        this.referUserId=referUserId;
    }

    
    public int getUserType() {
        return userType;
    }

    
    public void setUserType(int userType) {
        this.userType=userType;
    }

    
    public int getRegisterGameId() {
        return registerGameId;
    }

    
    public void setRegisterGameId(int registerGameId) {
        this.registerGameId=registerGameId;
    }

    
    public String getRegisterGameName() {
        return registerGameName;
    }

    
    public void setRegisterGameName(String registerGameName) {
        this.registerGameName=registerGameName;
    }

    
    public List<String> getResources() {
        return resources;
    }

    
    public void setResources(List<String> resources) {
        this.resources=resources;
    }

    public int getCpId() {
        return cpId;
    }

    public void setCpId(int cpId) {
        this.cpId=cpId;
    }
    
}
