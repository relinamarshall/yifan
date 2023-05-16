package com.yifan.common.core.exception;

import lombok.NoArgsConstructor;

/**
 * CheckedException
 *
 * @author Wenzhou
 * @since 2023/5/5 20:23
 */
@NoArgsConstructor
public class CheckedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CheckedException(String message) {
        super(message);
    }

    public CheckedException(Throwable cause) {
        super(cause);
    }

    public CheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

