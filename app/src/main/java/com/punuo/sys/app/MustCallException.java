package com.punuo.sys.app;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class MustCallException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MustCallException() {
        super();
    }

    public MustCallException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public MustCallException(String methodName, String where) {
        super("Must first call " + methodName + " in " + where);
    }

    public MustCallException(Throwable throwable) {
        super(throwable);
    }

    public MustCallException(String detailMessage) {
        super(detailMessage);
    }

    public MustCallException(String methodName, Class class1) {
        this(methodName, class1.getSimpleName());
    }
}
