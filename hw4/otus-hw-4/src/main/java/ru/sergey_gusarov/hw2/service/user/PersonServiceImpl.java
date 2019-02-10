package ru.sergey_gusarov.hw2.service.user;

import org.springframework.stereotype.Service;
import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.exception.DaoException;
import ru.sergey_gusarov.hw2.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository dao;

    // default @Autowired if one constructor
    public PersonServiceImpl(PersonRepository dao) {
        this.dao = dao;
    }

    public Person getByNameAndSurname(String name, String surname) throws DaoException {
        return dao.findByNameAndSurname(name, surname);
    }
}
