package ru.sergey_gusarov.hw5.repository;

import ru.sergey_gusarov.hw5.domain.Question;
import ru.sergey_gusarov.hw5.exception.DaoException;

import java.io.IOException;
import java.util.List;

public interface QuestionRepositoryFile {
    List<Question> findAll()
            throws IOException, DaoException;
}
