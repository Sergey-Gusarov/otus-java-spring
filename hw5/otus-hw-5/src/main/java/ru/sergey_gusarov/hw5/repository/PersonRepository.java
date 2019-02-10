package ru.sergey_gusarov.hw5.repository;

import ru.sergey_gusarov.hw5.domain.Person;
import ru.sergey_gusarov.hw5.exception.DaoException;

public interface PersonRepository {
    Person findByNameAndSurname(String name, String surname) throws DaoException;
}
