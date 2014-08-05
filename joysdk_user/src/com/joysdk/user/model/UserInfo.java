package com.joysdk.user.model;

import java.io.Serializable;
import java.util.Date;


public class UserInfo implements Serializable{

    private static final long serialVersionUID=1L;
    
    private int userId;
    
    private String nickName;
    
    private String realName;
    
    private String idCard;
    
    private String qq;
    
    private Date dtBirtherDay;
    
    private String avatarUrl;
    
    private int gender;
    
    private int lastLoginGameId;
    
    private Date lastLoginDate;
    
    
    public int getUserId() {
        return userId;
    }

    
    public void setUserId(int userId) {
        this.userId=userId;
    }

    
    public String getNickName() {
        return nickName;
    }

    
    public void setNickName(String nickName) {
        this.nickName=nickName;
    }

    
    public String getRealName() {
        return realName;
    }

    
    public void setRealName(String realName) {
        this.realName=realName;
    }

    
    public String getIdCard() {
        return idCard;
    }

    
    public void setIdCard(String idCard) {
        this.idCard=idCard;
    }

    
    public String getQq() {
        return qq;
    }

    
    public void setQq(String qq) {
        this.qq=qq;
    }

    
    public Date getDtBirtherDay() {
        return dtBirtherDay;
    }

    
    public void setDtBirtherDay(Date dtBirtherDay) {
        this.dtBirtherDay=dtBirtherDay;
    }

    
    public String getAvatarUrl() {
        return avatarUrl;
    }

    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl=avatarUrl;
    }

    
    public int getGender() {
        return gender;
    }

    
    public void setGender(int gender) {
        this.gender=gender;
    }

    
    public int getLastLoginGameId() {
        return lastLoginGameId;
    }

    
    public void setLastLoginGameId(int lastLoginGameId) {
        this.lastLoginGameId=lastLoginGameId;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate=lastLoginDate;
    }

}
