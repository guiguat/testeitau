package com.itau.teste.datasources.person;

import com.itau.teste.core.person.model.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "person")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private Integer age;
    private String country;

    public PersonEntity(Person person) {
        id = person.id();
        firstName = person.firstName();
        lastName = person.lastName();
        age = person.age();
        country = person.country();
    }

    Person toModel() {
        return new Person(id, firstName, lastName, age, country);
    }
}
