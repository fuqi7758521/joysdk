package com.joysdk.user.model;


public enum UserType {
    COMMON_USER(0), OTHER_USER(1), EMPLOYEE_USER(2),  CHANNEL_USER(3), CP_USER(4);
    
    private int type;
    
    private UserType(int type){
        this.type=type;
    }
    
    public int getType(){
        return this.type;
    }
   
}
