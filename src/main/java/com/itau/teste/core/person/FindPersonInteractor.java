package com.itau.teste.core.person;

import com.itau.teste.core.person.exception.PersonNotFoundException;
import com.itau.teste.core.person.model.Person;
import com.itau.teste.core.person.outbound.PersonDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindPersonInteractor {
    private final PersonDataSource personDataSource;
    public Person findOrThrow(Long id) {
        return personDataSource.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }
}
