package com.itau.teste.core.person;

import com.itau.teste.core.common.outbound.events.Event;
import com.itau.teste.core.common.outbound.events.EventBroker;
import com.itau.teste.core.person.exception.PersonAlreadyExistsException;
import com.itau.teste.core.person.exception.PersonNotFoundException;
import com.itau.teste.core.person.input.PersonUpdateData;
import com.itau.teste.core.person.model.Person;
import com.itau.teste.core.person.outbound.PersonDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdatePersonInteractorTest {
	private final PersonDataSource dataSource = mock(PersonDataSource.class);
	private final EventBroker eventBroker = mock(EventBroker.class);
	private final UpdatePersonInteractor interactor = new UpdatePersonInteractor(dataSource, eventBroker);

	@ParameterizedTest
	@MethodSource("provideUpdateData")
	void shouldUpdatePerson(PersonUpdateData updateData) {
		var personFound = new Person(1L, "a", "b", 10, "c");
		when(dataSource.findById(any())).thenReturn(Optional.of(personFound));
		when(dataSource.save(any())).thenAnswer((mock) -> mock.getArgument(0));
		doNothing().when(eventBroker).publish(any());
		var personSaved = new Person(
			personFound.id(),
			personFound.firstName(),
			personFound.lastName(),
			updateData.age() != null ? updateData.age() : personFound.age(),
			updateData.country() != null ? updateData.country() : personFound.country()
		);
		var saved = interactor.update(personSaved.id(), updateData);
		Assertions.assertEquals(personSaved, saved);
		verify(eventBroker, times(1))
			.publish(new Event("person:updated", personSaved));
		verify(dataSource, times(1))
			.findById(personFound.id());
		verify(dataSource, times(1))
			.save(personSaved);
	}

	@Test
	void shouldNotCreatePersonWhenFoundSaved() {
		when(dataSource.findById(any())).thenReturn(Optional.empty());
		var updateData = new PersonUpdateData(19, "BR");
		var id = new Random().nextLong();
		Assertions.assertThrows(PersonNotFoundException.class, () -> interactor.update(id, updateData));
		verify(dataSource, times(1)).findById(id);
		verify(eventBroker, times(0)).publish(any());
		verify(dataSource, times(0)).save(any());
	}

	private static Stream<PersonUpdateData> provideUpdateData() {
		return Stream.of(
			new PersonUpdateData(null, "BR"),
            new PersonUpdateData(new Random().nextInt(), null)
		);
	}
}
