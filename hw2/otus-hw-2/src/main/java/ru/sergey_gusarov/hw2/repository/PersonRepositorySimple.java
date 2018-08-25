package ru.sergey_gusarov.hw2.repository;

import org.springframework.context.MessageSource;
import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.exception.DaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public class PersonRepositorySimple implements PersonRepository {
    private List<Person> people = new ArrayList<Person>();

    private final MessageSource messageSource;

    public PersonRepositorySimple(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Person findByNameAndSurname(String name, String surname) throws DaoException {
        Boolean checkName = name == null || name.trim().isEmpty();
        Boolean checkSurname = surname == null || surname.trim().isEmpty();
        if (checkName && checkSurname)
            throw new DaoException(messageSource.getMessage("person.check.name.surname", null,
                    Locale.getDefault()));
        else if (checkName)
            throw new DaoException(messageSource.getMessage("person.check.name", null,
                    Locale.getDefault()));
        else if (checkSurname)
            throw new DaoException(messageSource.getMessage("person.check.surname", null,
                    Locale.getDefault()));
        Person person;
        try {
            person = people.stream()
                    .filter(p -> p.getName().equals(name) && p.getSurname().equals(surname))
                    .findFirst().get();
        } catch (NoSuchElementException ex) {
            person = new Person(name, surname);
            people.add(person);
            return person;
        }
        return person;
    }
}
