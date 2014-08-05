package com.joysdk.common.exception;


public class ApiJoySdkException extends JoySdkException {

    /**
     * 
     */
    private static final long serialVersionUID=1L;


    public ApiJoySdkException() {
        super();
    }

    public ApiJoySdkException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiJoySdkException(String message) {
        super(message);
    }

    public ApiJoySdkException(Throwable cause) {
        super(cause);
    }
    
    public ApiJoySdkException(int errId){
        super(String.valueOf(errId));
    }
}
