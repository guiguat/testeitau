package com.itau.teste.core.person.model;

public record Person(
    Long id,
    String firstName,
    String lastName,
    Integer age,
    String country
){ }
