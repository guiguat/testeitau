package com.itau.teste.transport.common.http;

import com.itau.teste.core.common.exception.BaseException;
import com.itau.teste.core.common.util.Generated;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Generated
public class ResponseExceptionHandler
    extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BaseException.class })
    protected ResponseEntity<Object> handleBaseException(BaseException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatusCode.valueOf(ex.getStatusCode()));
    }
}