package ru.sergey_gusarov.hw6.exception;

public class DaoException extends Exception {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public void printMessage() {
        System.err.println(this.getMessage());
    }
}
