package ru.sergey_gusarov.hw2.service.user;

import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.exception.DaoException;

public interface PersonService {
    Person getByNameAndSurname(String name, String surname) throws DaoException;
}
