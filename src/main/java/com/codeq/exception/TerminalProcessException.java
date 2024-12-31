package com.codeq.exception;

/*
 * exception is thrown at processor level
 */
public class TerminalProcessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public TerminalProcessException(int code, String message) {

        super(code, message);
    }

    public TerminalProcessException(int code, String message, Throwable throwable) {

        super(code, message, throwable);
    }

    public TerminalProcessException(String message, Throwable cause) {

        super(message, cause);
    }

    public TerminalProcessException(String message) {

        super(message);
    }

    public TerminalProcessException(Throwable cause) {

        super(cause);
    }

}
