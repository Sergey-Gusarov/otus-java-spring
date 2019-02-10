package ru.sergey_gusarov.hw5.service.user.login;

import ru.sergey_gusarov.hw5.domain.Person;
import ru.sergey_gusarov.hw5.exception.DaoException;

public interface LoginService {
    Person getUser() throws DaoException;
}
