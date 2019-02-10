package ru.sergey_gusarov.hw2.domain;

import java.util.List;

public class Question {
    private int id;
    private int ordinalNum;
    private String questionText;
    private int checkScore;
    private List<Answer> answers;

    public Question(int id, int ordinalNum, String questionText, int checkScore, List<Answer> answers) {
        this.id = id;
        this.ordinalNum = ordinalNum;
        this.questionText = questionText;
        this.checkScore = checkScore;
        this.answers = answers;
    }

    public Question(int ordinalNum, String questionText, int checkScore, List<Answer> answers) {
        this.id = -1;
        this.ordinalNum = ordinalNum;
        this.questionText = questionText;
        this.checkScore = checkScore;
        this.answers = answers;
    }

    public Question(int id, int checkScore, List<Answer> answers) {
        this.id = id;
        this.checkScore = checkScore;
        this.answers = answers;
    }

    public Question(int id, List<Answer> answers) {
        this.id = id;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheckScore() {
        return checkScore;
    }

    public void setCheckScore(int checkScore) {
        this.checkScore = checkScore;
    }

    public int getOrdinalNum() {
        return ordinalNum;
    }

    public void setOrdinalNum(int ordinalNum) {
        this.ordinalNum = ordinalNum;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
