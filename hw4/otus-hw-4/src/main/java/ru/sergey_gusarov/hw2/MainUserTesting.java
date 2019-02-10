package ru.sergey_gusarov.hw2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.domain.Question;
import ru.sergey_gusarov.hw2.domain.results.IntervieweeResultBase;
import ru.sergey_gusarov.hw2.exception.BizLogicException;
import ru.sergey_gusarov.hw2.exception.DaoException;
import ru.sergey_gusarov.hw2.repository.QuestionRepositoryFile;
import ru.sergey_gusarov.hw2.service.testing.TestingService;
import ru.sergey_gusarov.hw2.service.testing.results.ShowResultsService;
import ru.sergey_gusarov.hw2.service.user.login.LoginService;

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

    private final static Logger LOG = LoggerFactory.getLogger(MainUserTesting.class);

    public static void main(String[] args) {
        //Locale.setDefault(Locale.ENGLISH);  // - для настройки, если не выбирается автоматически
        Locale.setDefault(new Locale("ru", "RU"));
        System.out.println(Locale.getDefault());
        LOG.debug("Try load spring context");

        AnnotationConfigApplicationContext context = (AnnotationConfigApplicationContext) SpringApplication.run(MainUserTesting.class, args);
        LOG.debug("Finish load spring context");

        LOG.debug("Try get beans");
        QuestionRepositoryFile questionRepositoryFile = context.getBean(QuestionRepositoryFile.class);
        LoginService loginService = context.getBean(LoginService.class);
        TestingService testingService = context.getBean(TestingService.class);
        ShowResultsService showResultsService = context.getBean(ShowResultsService.class);
        LOG.debug("Finish getting beans");

        try {
            Person interviewee = loginService.getUser();
            List<Question> questions = questionRepositoryFile.findAll();
            IntervieweeResultBase intervieweeResult = testingService.startTest(questions, interviewee);
            showResultsService.showTestingResult(intervieweeResult);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("\n" + context.getMessage("main.exception", null, Locale.getDefault()));
            LOG.error("Real error", ex);
            return;
        } catch (DaoException ex) {
            ex.printStackTrace();
            ex.printMessage();
            LOG.error("Dao error", ex);
            return;
        } catch (BizLogicException ex) {
            ex.printStackTrace();
            ex.printMessage();
            LOG.error("Logic error", ex);
            return;
        }

        System.out.println("\n" + context.getMessage("main.end.test", null, Locale.getDefault()));
    }
}
