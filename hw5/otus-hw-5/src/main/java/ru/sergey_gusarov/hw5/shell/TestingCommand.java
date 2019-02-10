package ru.sergey_gusarov.hw5.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sergey_gusarov.hw5.MainUserTesting;
import ru.sergey_gusarov.hw5.domain.Person;
import ru.sergey_gusarov.hw5.domain.Question;
import ru.sergey_gusarov.hw5.domain.results.IntervieweeResultBase;
import ru.sergey_gusarov.hw5.exception.BizLogicException;
import ru.sergey_gusarov.hw5.exception.DaoException;
import ru.sergey_gusarov.hw5.repository.QuestionRepositoryFile;
import ru.sergey_gusarov.hw5.service.testing.TestingService;
import ru.sergey_gusarov.hw5.service.testing.results.ShowResultsService;
import ru.sergey_gusarov.hw5.service.user.login.LoginService;
import ru.sergey_gusarov.hw5.service.user.login.LoginServiceImplShell;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@ShellComponent
public class TestingCommand {
    private final Logger log = LoggerFactory.getLogger(MainUserTesting.class);

    private final QuestionRepositoryFile questionRepositoryFile;
    private final LoginService loginService;
    private final TestingService testingService;
    private final ShowResultsService showResultsService;
    private final MessageSource messageSource;

    @Autowired
    public TestingCommand(
                        QuestionRepositoryFile questionRepositoryFile,
                        LoginService loginService,
                        TestingService testingService, ShowResultsService showResultsService, MessageSource messageSource) {
        this.questionRepositoryFile = questionRepositoryFile;
        this.loginService = loginService;
        this.testingService = testingService;
        this.showResultsService = showResultsService;
        this.messageSource = messageSource;
    }

    @ShellMethod("Getting name and surname")
    public String start(
            @ShellOption String name,
            @ShellOption String surname
    ) {
        String text = name+"\n"+surname;
        byte[] buffer = text.getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        BufferedInputStream bis = new BufferedInputStream(in);

        try {
            ((LoginServiceImplShell) loginService).setInputStream(bis);
            Person interviewee = loginService.getUser();
            List<Question> questions = questionRepositoryFile.findAll();
            IntervieweeResultBase intervieweeResult = testingService.startTest(questions, interviewee);
            showResultsService.showTestingResult(intervieweeResult);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("\n" + messageSource.getMessage("main.exception", null, Locale.getDefault()));
            log.error("Real error", ex);
            return null;
        } catch (DaoException ex) {
            ex.printStackTrace();
            ex.printMessage();
            log.error("Dao error", ex);
            return null;
        } catch (BizLogicException ex) {
            ex.printStackTrace();
            ex.printMessage();
            log.error("Logic error", ex);
            return null;
        }

        System.out.println("\n" + messageSource.getMessage("main.end.test", null, Locale.getDefault()));
        return null;
    }
}
