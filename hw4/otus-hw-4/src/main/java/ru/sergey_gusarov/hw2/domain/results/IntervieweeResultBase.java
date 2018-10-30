package ru.sergey_gusarov.hw2.domain.results;

import ru.sergey_gusarov.hw2.domain.Person;
import ru.sergey_gusarov.hw2.domain.Question;

import java.util.ArrayList;
import java.util.List;

public abstract class IntervieweeResultBase {
    private Person person;
    private List<Question> questions = new ArrayList<Question>();

    public IntervieweeResultBase(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
