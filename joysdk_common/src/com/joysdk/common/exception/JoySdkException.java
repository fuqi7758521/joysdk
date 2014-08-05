package com.joysdk.common.exception;


public class JoySdkException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID=1L;

    public JoySdkException() {
        super();
    }

    public JoySdkException(String message, Throwable cause) {
        super(message, cause);
    }

    public JoySdkException(String message) {
        super(message);
    }

    public JoySdkException(Throwable cause) {
        super(cause);
    }
    
    public JoySdkException(int errId){
        super(String.valueOf(errId));
    }
}
