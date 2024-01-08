package com.itau.teste.core.person.outbound;

import com.itau.teste.core.person.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDataSource {
    Optional<Person> findById(Long id);

    List<Person> findAll();

    Optional<Person> findByName(String firstName, String lastName);

    Person save(Person person);

    void deleteById(Long id);
}