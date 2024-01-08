package com.itau.teste.core.person.input;

import com.itau.teste.core.person.model.Person;
import jakarta.validation.constraints.*;

public record PersonCreateRequest(
    @NotNull
    @Size(min = 2, max = 25)
    String firstName,
    @NotNull
    @Size(min = 2, max = 25)
    String lastName,
    @Max(150)
    @Min(18)
    @NotNull
    Integer age,
    @Size(max = 2, min = 2)
    String country
) {
    public Person toModel() {
        return new Person(null, firstName, lastName, age, country);
    }
}
