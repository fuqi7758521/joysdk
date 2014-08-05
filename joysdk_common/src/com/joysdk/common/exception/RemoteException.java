package com.joysdk.common.exception;


public class RemoteException extends Exception{

    private static final long serialVersionUID=1L;

    public RemoteException() {
        super();
    }

    public RemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteException(String message) {
        super(message);
    }

    public RemoteException(Throwable cause) {
        super(cause);
    }

}
