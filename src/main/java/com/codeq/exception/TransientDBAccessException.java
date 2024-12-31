package com.codeq.exception;

/*
 * This exception is thrown at service level
 */
public class TransientDBAccessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public TransientDBAccessException(int code, String message) {

        super(code, message);
    }

    public TransientDBAccessException(int code, String message, Throwable throwable) {

        super(code, message, throwable);
    }

    public TransientDBAccessException(String message, Throwable cause) {

        super(message, cause);
    }

    public TransientDBAccessException(String message) {

        super(message);
    }

    public TransientDBAccessException(Throwable cause) {

        super(cause);
    }

}
