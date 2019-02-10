package ru.sergey_gusarov.hw2.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.exception.DaoException;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositorySimpleTest {
    private final static String PERSON_NAME = "Name1";
    private final static String PERSON_SURNAME = "Surname1";

    private PersonRepository getPersonRepository() {
        Locale.setDefault(new Locale("ru", "RU"));
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("/i18/exception-messages", "/i18/shell-message");
        messageSource.setDefaultEncoding("UTF-8");
        return new PersonRepositorySimple(messageSource);
    }

    @Test
    @DisplayName("Поиск пользователя по фамилии и имени")
    void findByNameAndSurname() {
        /* Прохождение теста с поднятием контекста занимает 492 ms
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfigRus.class);
        PersonRepository personRepositorySimple = context.getBean(PersonRepository.class );
        */

        // Прохождение теста занимает 78 ms - похоже, так долго из-за того, что незакешированы файлы, т.е. этот тест выполняется первым
        PersonRepository personRepositorySimple = getPersonRepository();

        Throwable exceptionNameAndSurname = assertThrows(DaoException.class, () ->
                personRepositorySimple.findByNameAndSurname("", "")
        );
        assertEquals("Пустое значение имени и фамилии пользователя", exceptionNameAndSurname.getMessage());

        Throwable exceptionName = assertThrows(DaoException.class, () ->
                personRepositorySimple.findByNameAndSurname("", PERSON_SURNAME)
        );
        assertEquals("Пустое значение имени пользователя", exceptionName.getMessage());

        Throwable exceptionSurname = assertThrows(DaoException.class, () ->
                personRepositorySimple.findByNameAndSurname(PERSON_NAME, "")
        );
        assertEquals("Пустое значение фамилии пользователя", exceptionSurname.getMessage());

        Person firstPerson = null;
        try {
            firstPerson = personRepositorySimple.findByNameAndSurname(PERSON_NAME, PERSON_SURNAME);
        } catch (DaoException e) {
            e.printStackTrace();
            fail("Не удалось создать firstPerson");
        }
        assertEquals(PERSON_NAME, firstPerson.getName());
        assertEquals(PERSON_SURNAME, firstPerson.getSurname());
        Person newTryFirstPerson = null;
        try {
            newTryFirstPerson = personRepositorySimple.findByNameAndSurname(PERSON_NAME, PERSON_SURNAME);
        } catch (DaoException e) {
            e.printStackTrace();
            fail("Не удалось создать newTryFirstPerson");
        }
        assertEquals(PERSON_NAME, newTryFirstPerson.getName());
        assertEquals(PERSON_SURNAME, newTryFirstPerson.getSurname());
        assertEquals(newTryFirstPerson, firstPerson);
        Person secondPerson = null;
        try {
            secondPerson = personRepositorySimple.findByNameAndSurname("Name2", "Surname2");
        } catch (DaoException e) {
            e.printStackTrace();
            fail("Не удалось создать secondPerson");
        }
        assertEquals("Name2", secondPerson.getName());
        assertEquals("Surname2", secondPerson.getSurname());
        assertNotEquals(firstPerson, secondPerson);
    }


}