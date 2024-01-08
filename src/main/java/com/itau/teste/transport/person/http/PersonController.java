package com.itau.teste.transport.person.http;

import com.itau.teste.core.person.CreatePersonInteractor;
import com.itau.teste.core.person.FindPersonInteractor;
import com.itau.teste.core.person.UpdatePersonInteractor;
import com.itau.teste.core.person.model.Person;
import com.itau.teste.core.person.input.PersonUpdateData;
import com.itau.teste.core.person.outbound.PersonDataSource;
import com.itau.teste.core.person.input.PersonCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final FindPersonInteractor findPersonInteractor;
    private final CreatePersonInteractor createPersonInteractor;
    private final UpdatePersonInteractor updatePersonInteractor;
    private final PersonDataSource personDataSource;

    @GetMapping
    public List<Person> findAll() {
        return personDataSource.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable Long id) {
        return findPersonInteractor.findOrThrow(id);
    }

    @PostMapping
    public Person create(@RequestBody @Valid PersonCreateRequest request) {
        return createPersonInteractor.create(request.toModel());
    }

    @PatchMapping("/{id}")
    public Person update(@RequestBody @Valid PersonUpdateData request, @PathVariable Long id) {
        return updatePersonInteractor.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        personDataSource.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
