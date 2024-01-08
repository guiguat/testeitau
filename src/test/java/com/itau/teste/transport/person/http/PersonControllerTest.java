package com.itau.teste.transport.person.http;

import com.itau.teste.core.person.CreatePersonInteractor;
import com.itau.teste.core.person.FindPersonInteractor;
import com.itau.teste.core.person.UpdatePersonInteractor;
import com.itau.teste.core.person.input.PersonCreateRequest;
import com.itau.teste.core.person.input.PersonUpdateData;
import com.itau.teste.core.person.model.Person;
import com.itau.teste.core.person.outbound.PersonDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonControllerTest {

    private final FindPersonInteractor findPersonInteractor = mock(FindPersonInteractor.class);
    private final CreatePersonInteractor createPersonInteractor = mock(CreatePersonInteractor.class);
    private final UpdatePersonInteractor updatePersonInteractor = mock(UpdatePersonInteractor.class);
    private final PersonDataSource personDataSource = mock(PersonDataSource.class);
    private final PersonController controller = new PersonController(
        findPersonInteractor, createPersonInteractor, updatePersonInteractor, personDataSource
    );

    @Test
    void shouldGetById() {
        var expectedPerson = newPerson(1L);
        when(findPersonInteractor.findOrThrow(any())).thenReturn(expectedPerson);
        Assertions.assertEquals(expectedPerson, controller.findById(expectedPerson.id()));
        Mockito.verify(findPersonInteractor, times(1)).findOrThrow(expectedPerson.id());
    }

    @Test
    void shouldFindAll() {
        var expected = List.of(newPerson(1L));
        when(personDataSource.findAll()).thenReturn(expected);
        Assertions.assertEquals(expected, controller.findAll());
        Mockito.verify(personDataSource, times(1)).findAll();
    }

    @Test
    void shouldDeleteById() {
        var id = new Random().nextLong();
        doNothing().when(personDataSource).deleteById(any());
        Assertions.assertEquals(ResponseEntity.noContent().build(), controller.delete(id));
        Mockito.verify(personDataSource, times(1)).deleteById(id);
    }

    @Test
    void shouldCreate() {
        var person = newPerson(null);
        var request = new PersonCreateRequest(person.firstName(), person.lastName(), person.age(), person.country());
        when(createPersonInteractor.create(any())).thenAnswer(m -> m.getArgument(0));
        Assertions.assertEquals(person, controller.create(request));
        Mockito.verify(createPersonInteractor, times(1)).create(person);
    }

    @Test
    void shouldUpdate() {
        var person = newPerson(null);
        var request = new PersonUpdateData(person.age(), person.country());
        when(updatePersonInteractor.update(any(), any())).thenReturn(person);
        Assertions.assertEquals(person, controller.update(request, person.id()));
        Mockito.verify(updatePersonInteractor, times(1)).update(person.id(), request);
    }

    private Person newPerson(Long id) {
        return new Person(id, "a", "b", 10, "c");
    }
}
