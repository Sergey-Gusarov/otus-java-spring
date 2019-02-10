package ru.sergey_gusarov.hw5.service.user;

import ru.sergey_gusarov.hw5.domain.Person;
import ru.sergey_gusarov.hw5.exception.DaoException;

public interface PersonService {
    Person getByNameAndSurname(String name, String surname) throws DaoException;
}
