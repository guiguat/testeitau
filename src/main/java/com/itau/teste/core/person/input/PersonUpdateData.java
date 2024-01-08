package com.itau.teste.core.person.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonUpdateData(
    @Max(150)
    @Min(18)
    @NotNull
    Integer age,
    @Size(max = 2, min = 2)
    String country
) { }
