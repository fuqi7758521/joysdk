package com.joysdk.common.model;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private static final long serialVersionUID=6912092396034244372L;

    private boolean success;

    private int errorType=1;

    private String message;

    private T data;
    
    private String token;

    public Result() {
        this.success=Boolean.FALSE;
    }

    public Result(Boolean success) {
        this.success=success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success=success;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType=errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    
    public T getData() {
        return data;
    }

    
    public void setData(T data) {
        this.data=data;
    }

    
    public String getToken() {
        return token;
    }

    
    public void setToken(String token) {
        this.token=token;
    }
    
    
}
