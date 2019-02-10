package ru.sergey_gusarov.hw2.service.user.login;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.exception.BizLogicException;
import ru.sergey_gusarov.hw2.exception.DaoException;
import ru.sergey_gusarov.hw2.service.user.PersonService;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

@Service
public class LoginServiceImplShell implements LoginService {
    private final PersonService personService;
    // И это отличный парамтр конструктора.
    // Ок
    private final MessageSource messageSource;

    private InputStream inputStream = System.in;

    public LoginServiceImplShell(PersonService personService, MessageSource messageSource) {
        this.personService = personService;
        this.messageSource = messageSource;
    }

    public void setInputStream(InputStream inputStream) throws BizLogicException {
        if (inputStream == null)
            throw new BizLogicException(messageSource.getMessage("null.input.stream", null, Locale.getDefault()));
        this.inputStream = inputStream;
    }

    @Override
    public Person getUser() throws DaoException {
        String name;
        String surname;

        Scanner in = new Scanner(inputStream);
        System.out.print(messageSource.getMessage("input.name", null, Locale.getDefault()));
        name = in.nextLine();
        System.out.print(messageSource.getMessage("input.surname", null, Locale.getDefault()));
        surname = in.nextLine();

        return personService.getByNameAndSurname(name, surname);
    }
}
