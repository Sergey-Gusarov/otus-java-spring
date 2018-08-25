package ru.sergey_gusarov.hw2.service.user;

import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.exception.DaoException;
import ru.sergey_gusarov.hw2.repository.PersonRepository;


public class PersonServiceImpl implements PersonService {
    // ТОгда уж и final
    // Ok проглядел.
    private final PersonRepository dao;

    // default @Autowired if one constructor
    public PersonServiceImpl(PersonRepository dao) {
        this.dao = dao;
    }

    public Person getByNameAndSurname(String name, String surname) throws DaoException {
        return dao.findByNameAndSurname(name, surname);
    }
}
