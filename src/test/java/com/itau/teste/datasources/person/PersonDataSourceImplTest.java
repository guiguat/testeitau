package com.itau.teste.datasources.person;

import com.itau.teste.core.person.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonDataSourceImplTest {
    private final PersonRepository repository = mock(PersonRepository.class);
    private final PersonDataSourceImpl dataSource = new PersonDataSourceImpl(repository);

    @Test
    void shouldFindById() {
        var expectedPerson = newPerson();

        when(repository.findById(any())).thenReturn(Optional.of(new PersonEntity(expectedPerson)));
        var found = dataSource.findById(expectedPerson.id());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(expectedPerson, found.get());
        verify(repository, times(1))
            .findById(expectedPerson.id());
    }

    @Test
    void shouldReturnEmptyIfNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        var found = dataSource.findById(new Random().nextLong());
        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindAll() {
        var expectedPerson = newPerson();
        when(repository.findAll()).thenReturn(List.of(new PersonEntity(expectedPerson)));
        var found = dataSource.findAll();
        Assertions.assertEquals(List.of(expectedPerson), found);
        verify(repository, times(1))
            .findAll();
    }

    @Test
    void shouldFindByName() {
        var expectedPerson = newPerson();
        when(repository.findByFirstNameAndLastName(any(), any()))
            .thenReturn(Optional.of(new PersonEntity(expectedPerson)));
        var found = dataSource.findByName(expectedPerson.firstName(), expectedPerson.lastName());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(expectedPerson, found.get());
        verify(repository, times(1))
            .findByFirstNameAndLastName(expectedPerson.firstName(), expectedPerson.lastName());
    }

    @Test
    void shouldSave() {
        var expectedPerson = newPerson();
        var captor = ArgumentCaptor.forClass(PersonEntity.class);
        when(repository.save(any())).thenAnswer(m -> m.getArgument(0));
        var saved = dataSource.save(expectedPerson);
        Assertions.assertEquals(expectedPerson, saved);
        verify(repository, times(1)).save(captor.capture());
        Assertions.assertEquals(expectedPerson, captor.getValue().toModel());
    }

    @Test
    void shouldDeleteById() {
        var id = new Random().nextLong();
        Mockito.doNothing().when(repository).deleteById(any());
        dataSource.deleteById(id);
        verify(repository, times(1)).deleteById(id);
    }

    private Person newPerson() {
        return new Person(1L, "a", "b", 10, "c");
    }
}
