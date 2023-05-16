package com.yifan.common.core.exception;

/**
 * ValidateCodeException
 *
 * @author Wenzhou
 * @since 2023/5/5 20:28
 */
public class ValidateCodeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidateCodeException() {
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}