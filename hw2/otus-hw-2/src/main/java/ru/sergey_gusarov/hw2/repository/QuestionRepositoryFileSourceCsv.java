package ru.sergey_gusarov.hw2.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import ru.sergey_gusarov.hw2.domain.Answer;
import ru.sergey_gusarov.hw2.domain.Question;
import ru.sergey_gusarov.hw2.exception.DaoException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository("questionRepositoryFile")
public class QuestionRepositoryFileSourceCsv implements QuestionRepositoryFile {
    private final static int QUESTION_START_NUM = 1;

    private final MessageSource messageSource;

    private final int countQuestionInFile;
    private final String questionsFileName;

    //А договривались через конструктор.
    // Ок - на полях = bad practice
    public QuestionRepositoryFileSourceCsv(@Value("${testing.question.file}") String questionsFileName,
                                           @Value("${testing.question.max_count}") int countQuestionInFile,
                                           MessageSource messageSource) {
        this.messageSource = messageSource;
        this.countQuestionInFile = countQuestionInFile;
        String lang = Locale.getDefault().toString();
        if ("ru_RU".equals(lang))
            this.questionsFileName = questionsFileName;
        else {
            if (lang.contains("_"))
                lang = lang.substring(0, lang.indexOf("_"));
            String sb = questionsFileName.substring(0, questionsFileName.lastIndexOf(".")) +
                    "_" + lang +
                    questionsFileName.substring(questionsFileName.lastIndexOf("."), questionsFileName.length());
            this.questionsFileName = sb;
        }
    }

    @Override
    public List<Question> findAll() throws IOException, DaoException {
        if (questionsFileName != null)
            return loadFile(questionsFileName);
        else
            throw new DaoException(messageSource.getMessage("question.file.dont.set", null,
                    Locale.getDefault()));
    }

    private List<Question> loadFile(String fileName) throws IOException, DaoException {
        List<Question> questionList = new ArrayList<Question>();
        Reader inCsvFile;
        try {
            inCsvFile = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader().parse(inCsvFile);
            Integer countQuestions = QUESTION_START_NUM;
            for (CSVRecord record : records) {
                List<Answer> answers = new ArrayList<Answer>();
                for (Integer i = QUESTION_START_NUM; i <= countQuestionInFile; i++) {
                    String colName = "Answer" + i.toString();
                    String answerStr = record.get(colName);
                    Integer score = Integer.valueOf(record.get("Score" + i.toString()));
                    answers.add(new Answer(answerStr, score));
                }
                Integer idQuestion = Integer.valueOf(record.get("Id"));
                String questionStr = record.get("Question");
                Integer checkScore = Integer.valueOf(record.get("CheckScore"));
                Question question = new Question(idQuestion, countQuestions++, questionStr, checkScore, answers);
                questionList.add(question);
            }
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        } catch (IllegalStateException | IllegalArgumentException ex) {
            throw new DaoException(messageSource.getMessage("question.file.error.read", null,
                    Locale.getDefault()), ex);
        }
        return questionList;
    }
}
