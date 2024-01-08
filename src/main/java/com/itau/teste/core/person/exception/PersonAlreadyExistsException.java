package com.itau.teste.core.person.exception;

import com.itau.teste.core.common.exception.BaseException;

public class PersonAlreadyExistsException extends BaseException {
    public PersonAlreadyExistsException() {
        super("There's already someone with that name", 400);
    }
}
