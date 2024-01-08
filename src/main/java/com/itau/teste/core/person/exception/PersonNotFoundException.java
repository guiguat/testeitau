package com.itau.teste.core.person.exception;

import com.itau.teste.core.common.exception.BaseException;

public class PersonNotFoundException extends BaseException {
    public PersonNotFoundException(Long id) {
        super("Person with id " + id + " not found", 404);
    }
}
