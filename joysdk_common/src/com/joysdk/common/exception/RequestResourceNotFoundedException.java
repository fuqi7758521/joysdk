package com.joysdk.common.exception;

public class RequestResourceNotFoundedException extends JoySdkException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4538483810269360214L;

	public RequestResourceNotFoundedException() {
		super();
	}

	public RequestResourceNotFoundedException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestResourceNotFoundedException(String message) {
		super(message);
	}

	public RequestResourceNotFoundedException(Throwable cause) {
		super(cause);
	}
}
