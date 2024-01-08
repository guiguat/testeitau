package com.itau.teste.core.person;

import com.itau.teste.core.person.exception.PersonNotFoundException;
import com.itau.teste.core.person.model.Person;
import com.itau.teste.core.person.outbound.PersonDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindPersonInteractorTest {
	private final PersonDataSource dataSource = mock(PersonDataSource.class);
	private final FindPersonInteractor interactor = new FindPersonInteractor(dataSource);

	@Test
	void shouldFindPerson() {
		var expectedPerson = new Person(1L, "a", "b", 10, "c");
		when(dataSource.findById(any())).thenReturn(Optional.of(expectedPerson));

		var found = interactor.findOrThrow(expectedPerson.id());
		Assertions.assertEquals(expectedPerson, found);
		verify(dataSource, times(1))
			.findById(expectedPerson.id());
	}

	@Test
	void shouldThrowIfPersonNotFound() {
		var expectedPerson = new Person(1L, "a", "b", 10, "c");
		when(dataSource.findById(any())).thenReturn(Optional.empty());
		Assertions.assertThrows(PersonNotFoundException.class, () -> interactor.findOrThrow(expectedPerson.id()));
		verify(dataSource, times(1))
			.findById(expectedPerson.id());
	}

}
