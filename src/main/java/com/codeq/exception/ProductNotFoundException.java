package com.codeq.exception;

public class ProductNotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(int code, String message) {

        super(code, message);
    }

    public ProductNotFoundException(int code, String message, Throwable throwable) {

        super(code, message, throwable);
    }

    public ProductNotFoundException(String message, Throwable cause) {

        super(message, cause);
    }

    public ProductNotFoundException(String message) {

        super(message);
    }

    public ProductNotFoundException(Throwable cause) {

        super(cause);
    }

}
