package com.itau.teste.core.person;

import com.itau.teste.core.common.outbound.events.Event;
import com.itau.teste.core.common.outbound.events.EventBroker;
import com.itau.teste.core.person.exception.PersonAlreadyExistsException;
import com.itau.teste.core.person.model.Person;
import com.itau.teste.core.person.outbound.PersonDataSource;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePersonInteractor {
    private final PersonDataSource personDataSource;
    private final EventBroker eventBroker;
    private static final String CREATED_EVENT_NAME = "person:created";
    private static final Logger logger = LoggerFactory.getLogger(CreatePersonInteractor.class);

    public Person create(Person person) {
        personDataSource.findByName(person.firstName(), person.lastName())
            .ifPresent(p -> { throw new PersonAlreadyExistsException(); });
        var savedPerson = personDataSource.save(person);
        eventBroker.publish(new Event(CREATED_EVENT_NAME, savedPerson));
        logger.info("created new person {}", savedPerson);
        return savedPerson;
    }
}
