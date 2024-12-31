package com.codeq.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Data
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private Throwable throwable;

    public BaseException(int code, String message, Throwable throwable) {

        super();
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }

    public BaseException(int code, String message) {

        super();
        this.code = code;
        this.message = message;
    }

    public BaseException(String message, Throwable cause) {

        super(message, cause);
    }

    public BaseException(String message) {

        super(message);
    }

    public BaseException(Throwable cause) {

        super(cause);
    }

}
