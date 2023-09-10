package com.chirag.spring6restmvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//instead of controller advice or ExceptionHandler method in controller, we can directly define response-status
// annotation on the top of the custom exception and Spring would automatically throw that response status when
// encountered with that exception
//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value not found")

public class UnknownException extends RuntimeException{
    public UnknownException() {
    }

    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownException(Throwable cause) {
        super(cause);
    }

    public UnknownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
