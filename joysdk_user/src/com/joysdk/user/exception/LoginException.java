package com.joysdk.user.exception;

import com.joysdk.common.exception.ApiJoySdkException;

public class LoginException extends ApiJoySdkException {

	private static final long serialVersionUID = -9149099761296783769L;
	
    public LoginException() {
        super();
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }
    
    public LoginException(int errId){
        super(String.valueOf(errId));
    }

}
