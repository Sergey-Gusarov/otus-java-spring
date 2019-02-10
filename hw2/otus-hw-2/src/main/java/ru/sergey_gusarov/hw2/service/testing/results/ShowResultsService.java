package ru.sergey_gusarov.hw2.service.testing.results;

import ru.sergey_gusarov.hw2.domain.results.IntervieweeResultBase;
import ru.sergey_gusarov.hw2.exception.BizLogicException;

public interface ShowResultsService {
    void showTestingResult(IntervieweeResultBase intervieweeResult) throws BizLogicException;
}
