package com.bear.storage;

/**
 * Created by bear on 16-12-23.
 */
public class SimpleRedisException extends Exception {

    public SimpleRedisException() {
        super();
    }

    public SimpleRedisException(Throwable cause) {
        super(cause);
    }

    protected SimpleRedisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SimpleRedisException(String message, Throwable cause) {
        super(message, cause);
    }

}
