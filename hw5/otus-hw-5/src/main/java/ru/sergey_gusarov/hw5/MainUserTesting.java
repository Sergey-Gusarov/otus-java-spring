package ru.sergey_gusarov.hw5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.sergey_gusarov.hw5.domain.Person;
import ru.sergey_gusarov.hw5.domain.Question;
import ru.sergey_gusarov.hw5.domain.results.IntervieweeResultBase;
import ru.sergey_gusarov.hw5.exception.BizLogicException;
import ru.sergey_gusarov.hw5.exception.DaoException;
import ru.sergey_gusarov.hw5.repository.QuestionRepositoryFile;
import ru.sergey_gusarov.hw5.service.testing.TestingService;
import ru.sergey_gusarov.hw5.service.testing.results.ShowResultsService;
import ru.sergey_gusarov.hw5.service.user.login.LoginService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


@SpringBootApplication
public class MainUserTesting {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasenames("/i18/exception-messages", "/i18/shell-message");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    public static void main(String[] args) {
        //Locale.setDefault(Locale.ENGLISH);  // - для настройки, если не выбирается автоматически
        Locale.setDefault(new Locale("ru", "RU"));
        System.out.println(Locale.getDefault());
        AnnotationConfigApplicationContext context = (AnnotationConfigApplicationContext) SpringApplication.run(MainUserTesting.class, args);
    }
}
