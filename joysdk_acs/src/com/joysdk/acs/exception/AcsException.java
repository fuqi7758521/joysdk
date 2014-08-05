package com.joysdk.acs.exception;

/**
 * 权限异常实体类
 * @author qi.fu@yunyoyo.cn
 * @date 2014-07-16 15:22
 */
public class AcsException extends Exception{

	private static final long serialVersionUID=1L;

    public AcsException() {
        super();
    }

    public AcsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AcsException(String message) {
        super(message);
    }

    public AcsException(Throwable cause) {
        super(cause);
    }
}
