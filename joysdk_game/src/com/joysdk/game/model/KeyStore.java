package com.joysdk.game.model;


public class KeyStore {

    private int id;
    
    private String name;
    
    private String fileId;
    
    private String alias;
    
    private String storePass;
    
    private String mainPass;
    
    private String commonName;
    
    private String orgName;
    
    private int cpId;

    
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

    
    public String getFileId() {
        return fileId;
    }

    
    public void setFileId(String fileId) {
        this.fileId=fileId;
    }

    
    public String getAlias() {
        return alias;
    }

    
    public void setAlias(String alias) {
        this.alias=alias;
    }

    
    public String getStorePass() {
        return storePass;
    }

    
    public void setStorePass(String storePass) {
        this.storePass=storePass;
    }

    
    public String getMainPass() {
        return mainPass;
    }

    
    public void setMainPass(String mainPass) {
        this.mainPass=mainPass;
    }

    
    public String getCommonName() {
        return commonName;
    }

    
    public void setCommonName(String commonName) {
        this.commonName=commonName;
    }

    
    public String getOrgName() {
        return orgName;
    }

    
    public void setOrgName(String orgName) {
        this.orgName=orgName;
    }

    
    public int getCpId() {
        return cpId;
    }

    
    public void setCpId(int cpId) {
        this.cpId=cpId;
    }
    
}
