package ru.sergey_gusarov.hw2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sergey_gusarov.hw2.config.AppConfigRus;
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


// Вот по идее должен загрузиться после этого. classpath:/aplication.properties возможно ещё может помочь, но должно и так.
// Вынес в настроечный файл

public class MainUserTesting {
// А почему нет интерфейса?
// Это мэйн класс

    private final static Logger LOG = LoggerFactory.getLogger(MainUserTesting.class);
    //Если static, то LOG, но лучше нестатическими делать переменными логгер.
    // В мэйн классе на сколько знаю это нормально, в других классах абсолютно согласен

    public static void main(String[] args) {
        //Locale.setDefault(Locale.ENGLISH);  // - для настройки, если не выбирается автоматически
        //Locale.setDefault(new Locale("ru", "RU"));
        // Locale rus = new Locale("ru", "RU");
        System.out.println(Locale.getDefault());

        //context.refresh() после этого можно написать, но лучше просто передать в параметры констркутора аспекта
        // Ok
        LOG.debug("Try load spring context");
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfigRus.class);
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
