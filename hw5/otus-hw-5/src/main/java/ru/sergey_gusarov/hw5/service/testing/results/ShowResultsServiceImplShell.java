package ru.sergey_gusarov.hw5.service.testing.results;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.sergey_gusarov.hw5.domain.results.IntervieweeResultBase;
import ru.sergey_gusarov.hw5.exception.BizLogicException;
import ru.sergey_gusarov.hw5.util.ResultCheckHelper;

import java.util.Locale;

@Service
public class ShowResultsServiceImplShell implements ShowResultsService {
    private final ResultCheckHelper resultCheckHelper;
    private final MessageSource messageSource;

    public ShowResultsServiceImplShell(ResultCheckHelper resultCheckHelper, MessageSource messageSource) {
        this.resultCheckHelper = resultCheckHelper;
        this.messageSource = messageSource;
    }

    @Override
    public void showTestingResult(IntervieweeResultBase intervieweeResult) throws BizLogicException {
        if (intervieweeResult == null) {
            throw new BizLogicException(messageSource.getMessage("havent.user", null, Locale.getDefault()));
        }
        boolean isTestPass;
        Integer sum;
        try {
            isTestPass = resultCheckHelper.isTestPass(intervieweeResult.getQuestions());
            sum = resultCheckHelper.getSumScore(intervieweeResult.getQuestions());
        } catch (BizLogicException ex) {
            throw ex;
        }
        if (isTestPass)
            System.out.println(messageSource.getMessage("result.pass", new Object[]{
                    intervieweeResult.getPerson().getFullName(), sum}, Locale.getDefault()));
        else
            System.out.println(messageSource.getMessage("result.fail", new Object[]{
                    intervieweeResult.getPerson().getFullName(), sum}, Locale.getDefault()));
    }
}
