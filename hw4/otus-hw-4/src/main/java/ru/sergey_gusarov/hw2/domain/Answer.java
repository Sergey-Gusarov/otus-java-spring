package ru.sergey_gusarov.hw2.domain;

public class Answer {
    private int id;
    private String answerText;
    private int score;

    public Answer(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public Answer(int id, String answerText, int score) {
        this.id = id;
        this.answerText = answerText;
        this.score = score;
    }

    public Answer(String answerText, int score) {
        this.answerText = answerText;
        this.score = score;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
