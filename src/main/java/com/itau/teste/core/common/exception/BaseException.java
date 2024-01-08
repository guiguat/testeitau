package com.itau.teste.core.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final String message;
    private final Integer statusCode;

    public BaseException(String message, Integer statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

}
