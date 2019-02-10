package ru.sergey_gusarov.hw5.service.testing;

import ru.sergey_gusarov.hw5.domain.Person;
import ru.sergey_gusarov.hw5.domain.Question;
import ru.sergey_gusarov.hw5.domain.results.IntervieweeResultBase;
import ru.sergey_gusarov.hw5.exception.BizLogicException;

import java.util.List;

public interface TestingService {
    IntervieweeResultBase startTest(List<Question> questions, Person interviewee) throws BizLogicException;
}
