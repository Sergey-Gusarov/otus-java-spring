package ru.sergey_gusarov.hw5.shell;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Input;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.shell.result.DefaultResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sergey_gusarov.hw5.domain.Person;
import ru.sergey_gusarov.hw5.domain.Question;
import ru.sergey_gusarov.hw5.domain.results.IntervieweeResultBase;
import ru.sergey_gusarov.hw5.exception.BizLogicException;
import ru.sergey_gusarov.hw5.exception.DaoException;
import ru.sergey_gusarov.hw5.repository.QuestionRepositoryFile;
import ru.sergey_gusarov.hw5.service.testing.TestingService;
import ru.sergey_gusarov.hw5.service.testing.TestingServiceImplFile;
import ru.sergey_gusarov.hw5.service.user.login.LoginServiceImplShell;
import ru.sergey_gusarov.hw5.util.ResultCheckHelper;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"})

class TestingCommandTest {
    private final static int CORRECT_SCORE = 7;

    @Autowired
    private Shell shell;
    @Autowired
    private ResultCheckHelper resultCheckHelper;
    @Autowired
    private QuestionRepositoryFile questionRepositoryFile;
    @Autowired
    private TestingService testingService;

    private List<Question> dummyQuestion() {
        List<Question> questions = null;
        try {
            questions = questionRepositoryFile.findAll();
        } catch (DaoException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return questions;
    }

    @BeforeAll
    private static void setupUpLocale(){
        Locale.setDefault(new Locale("ru", "RU"));
        //Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    @DisplayName("Запуск через shell")
    void start() {

        String text = "3\n2,3\n1\n1\n1,2";
        byte[] buffer = text.getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        BufferedInputStream bis = new BufferedInputStream(in);

        try {
            ((TestingServiceImplFile) testingService).setInputStream(bis);
        } catch (BizLogicException e) {
            e.printStackTrace();
        }

        assertDoesNotThrow(() ->
                {
                    Object result = shell.evaluate(new Input() {
                        @Override
                        public String rawText() {
                            return "start name1 surname1";
                        }
                    });
                }
                , "Запуск через команду start и проход по успешному пути не удался");
    }
}