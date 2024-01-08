package com.itau.teste.core.person;

import com.itau.teste.core.common.outbound.events.Event;
import com.itau.teste.core.common.outbound.events.EventBroker;
import com.itau.teste.core.person.exception.PersonNotFoundException;
import com.itau.teste.core.person.model.Person;
import com.itau.teste.core.person.input.PersonUpdateData;
import com.itau.teste.core.person.outbound.PersonDataSource;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePersonInteractor {
    private final PersonDataSource personDataSource;
    private final EventBroker eventBroker;
    private static final String UPDATE_EVENT_NAME = "person:updated";
    private static final Logger logger = LoggerFactory.getLogger(UpdatePersonInteractor.class);

    public Person update(Long id, PersonUpdateData update) {
        var person = personDataSource.findById(id)
            .orElseThrow(() -> new PersonNotFoundException(id));
        var savedPerson = personDataSource.save(applyUpdate(person, update));
        eventBroker.publish(new Event(UPDATE_EVENT_NAME, savedPerson));
        logger.info("updated person with id={} before: {}, after: {}", id, person, savedPerson);
        return savedPerson;
    }

    private Person applyUpdate(Person person, PersonUpdateData update) {
        return new Person(
            person.id(),
            person.firstName(),
            person.lastName(),
            update.age() != null ? update.age() : person.age(),
            update.country() != null ? update.country() : person.country()
        );
    }
}
