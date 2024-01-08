package com.itau.teste.datasources.person;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface PersonRepository extends ListCrudRepository<PersonEntity, Long> {
    public Optional<PersonEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
