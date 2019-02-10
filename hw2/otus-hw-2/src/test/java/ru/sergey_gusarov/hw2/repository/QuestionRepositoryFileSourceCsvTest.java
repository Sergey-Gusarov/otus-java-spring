package ru.sergey_gusarov.hw2.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.sergey_gusarov.hw2.domain.Question;
import ru.sergey_gusarov.hw2.exception.DaoException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@PropertySource("classpath:/application.properties")
class QuestionRepositoryFileSourceCsvTest {
    private final static String QUESTIONS_FILE = "src/test/resources/testQuestions.csv";
    private final static int COUNT_QUESTION = 4;

    private QuestionRepositoryFile getQuestionRepositoryFile() {
        Locale.setDefault(new Locale("ru", "RU"));
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("/i18/exception-messages", "/i18/shell-message");
        messageSource.setDefaultEncoding("UTF-8");
        return new QuestionRepositoryFileSourceCsv(QUESTIONS_FILE, COUNT_QUESTION, messageSource);
    }

    @Test
    @DisplayName("Получение вопросов")
    void findAll() {
        /* Прохождение теста с поднятием контекста занимает 446 ms
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfigRus.class);
        QuestionRepositoryFile questionRepositoryFile = context.getBean(QuestionRepositoryFile.class);
        */
        // Прохождение теста занимает 14 ms
        QuestionRepositoryFile questionRepositoryFile = getQuestionRepositoryFile();

        List<Question> questions = null;
        try {
            questions = questionRepositoryFile.findAll();
        } catch (IOException ex) {
            ex.printStackTrace();
            fail("IOException");
        } catch (DaoException ex) {
            ex.printStackTrace();
            fail("BizLogicException");
        }
        assertNotNull(questions, "Объект с вопросами пустой");
        assertEquals("111Мне и так норм111_ТЕСТОВЫЙ ЭЛЕМЕНТ", questions.get(1).getAnswers().get(0).getAnswerText(), "Чтение из файла текста ответа");

    }



}