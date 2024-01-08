package com.itau.teste.core.person;

import com.itau.teste.core.common.outbound.events.Event;
import com.itau.teste.core.common.outbound.events.EventBroker;
import com.itau.teste.core.person.exception.PersonAlreadyExistsException;
import com.itau.teste.core.person.model.Person;
import com.itau.teste.core.person.outbound.PersonDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreatePersonInteractorTest {
	private final PersonDataSource dataSource = mock(PersonDataSource.class);
	private final EventBroker eventBroker = mock(EventBroker.class);
	private final CreatePersonInteractor interactor = new CreatePersonInteractor(dataSource, eventBroker);

	@Test
	void shouldCreatePersonAndNotify() {
		when(dataSource.findByName(any(), any())).thenReturn(Optional.empty());
		when(dataSource.save(any())).thenAnswer((mock) -> {
			Person arg = mock.getArgument(0);
			return new Person(1L, arg.firstName(), arg.lastName(), arg.age(), arg.country());
		});
		doNothing().when(eventBroker).publish(any());
		var expectedPerson = new Person(1L, "a", "b", 10, "c");
		var personToSave = new Person(
			null,
			expectedPerson.firstName(),
			expectedPerson.lastName(),
			expectedPerson.age(),
			expectedPerson.country()
		);
		var saved = interactor.create(personToSave);
		Assertions.assertEquals(expectedPerson, saved);
		verify(eventBroker, times(1))
			.publish(new Event("person:created", expectedPerson));
		verify(dataSource, times(1))
			.findByName(expectedPerson.firstName(), expectedPerson.lastName());
		verify(dataSource, times(1))
			.save(personToSave);
	}

	@Test
	void shouldNotCreatePersonWhenFoundSaved() {
		var expectedPerson = new Person(1L, "a", "b", 10, "c");
		when(dataSource.findByName(any(), any())).thenReturn(Optional.of(expectedPerson));
		Assertions.assertThrows(PersonAlreadyExistsException.class, () -> interactor.create(expectedPerson));
		verify(eventBroker, times(0)).publish(any());
		verify(dataSource, times(1))
			.findByName(expectedPerson.firstName(), expectedPerson.lastName());
		verify(dataSource, times(0)).save(any());
	}

}
