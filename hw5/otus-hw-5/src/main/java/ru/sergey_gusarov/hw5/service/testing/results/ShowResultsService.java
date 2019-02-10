package ru.sergey_gusarov.hw5.service.testing.results;

import ru.sergey_gusarov.hw5.domain.results.IntervieweeResultBase;
import ru.sergey_gusarov.hw5.exception.BizLogicException;

public interface ShowResultsService {
    void showTestingResult(IntervieweeResultBase intervieweeResult) throws BizLogicException;
}
