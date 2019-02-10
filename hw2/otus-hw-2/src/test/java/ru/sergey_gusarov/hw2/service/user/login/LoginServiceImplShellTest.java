package ru.sergey_gusarov.hw2.service.user.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.exception.BizLogicException;
import ru.sergey_gusarov.hw2.repository.PersonRepository;
import ru.sergey_gusarov.hw2.repository.PersonRepositorySimple;
import ru.sergey_gusarov.hw2.service.user.PersonService;
import ru.sergey_gusarov.hw2.service.user.PersonServiceImpl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceImplShellTest {

    private LoginService getLoginService() {
        Locale.setDefault(new Locale("ru", "RU"));
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("/i18/exception-messages", "/i18/shell-message");
        messageSource.setDefaultEncoding("UTF-8");
        PersonRepository personRepository = new PersonRepositorySimple(messageSource);
        PersonService personService = new PersonServiceImpl(personRepository);
        return new LoginServiceImplShell(personService, messageSource);
    }

    @Test
    @DisplayName("Получение пользователя")
    void getUser() {
        LoginService loginService = getLoginService();

        Throwable trySetNullForInputStream = assertThrows(BizLogicException.class, () ->
                ((LoginServiceImplShell) loginService).setInputStream(null)
        );
        assertEquals("Передан пустой поток!", trySetNullForInputStream.getMessage(), "Обработка передачи пустого потока ввода");

        String text = "Name1\nSurname1";
        byte[] buffer = text.getBytes();
        ByteArrayInputStream in1 = new ByteArrayInputStream(buffer);
        BufferedInputStream bis1 = new BufferedInputStream(in1);

        assertDoesNotThrow(() ->
                        ((LoginServiceImplShell) loginService).setInputStream(bis1)
                , "Установка тестового потока ввода прошла не успешно");

        AtomicReference<Person> atomicReference = new AtomicReference<Person>();
        assertDoesNotThrow(() -> atomicReference.set(loginService.getUser())
                , "Пользователь не поднят успешно");
        Person firstTryGetPerson = atomicReference.get();
        assertEquals("Name1 Surname1", firstTryGetPerson.getFullName());

        // Проверка на повторное взятие персоны
        ByteArrayInputStream in2 = new ByteArrayInputStream(buffer);
        BufferedInputStream bis2 = new BufferedInputStream(in2);
        assertDoesNotThrow(() ->
                        ((LoginServiceImplShell) loginService).setInputStream(bis2)
                , "Установка тестового потока ввода прошла не успешно");
        //Повторный вызов пользователя
        assertDoesNotThrow(() -> {
                    Person person = loginService.getUser();
                    atomicReference.set(person);
                }
                , "Пользователь поднят не успешно");
        Person secondTryGetPerson = atomicReference.get();
        assertEquals(firstTryGetPerson, secondTryGetPerson, "Не одна и таже персона");
    }
}