package com.itau.teste.datasources.person;

import com.itau.teste.core.person.model.Person;
import com.itau.teste.core.person.outbound.PersonDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonDataSourceImpl implements PersonDataSource {
    private final PersonRepository repository;

    @Override
    public Optional<Person> findById(Long id) {
        return repository.findById(id).map(PersonEntity::toModel);
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll().stream().map(PersonEntity::toModel).toList();
    }

    @Override
    public Optional<Person> findByName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName).map(PersonEntity::toModel);
    }

    @Override
    public Person save(Person person) {
        return repository.save(new PersonEntity(person)).toModel();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
