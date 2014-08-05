package com.joysdk.user.exception;

import com.joysdk.common.exception.ApiJoySdkException;

public class UserException extends ApiJoySdkException {

	private static final long serialVersionUID = -9149099761296783769L;
	
    public UserException() {
        super();
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
    
    public UserException(int errId){
        super(String.valueOf(errId));
    }

}
