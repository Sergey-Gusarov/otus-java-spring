package ru.sergey_gusarov.hw2.service.testing;

import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.domain.Question;
import ru.sergey_gusarov.hw2.domain.results.IntervieweeResultBase;
import ru.sergey_gusarov.hw2.exception.BizLogicException;

import java.util.List;

public interface TestingService {
    IntervieweeResultBase startTest(List<Question> questions, Person interviewee) throws BizLogicException;
}
