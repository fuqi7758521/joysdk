package com.joysdk.common.exception;

public class AuthenticationException extends JoySdkException {

    /**
	 * 
	 */
    private static final long serialVersionUID=-5261544442627314763L;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(int errId) {
        super(String.valueOf(errId));
    }
}
