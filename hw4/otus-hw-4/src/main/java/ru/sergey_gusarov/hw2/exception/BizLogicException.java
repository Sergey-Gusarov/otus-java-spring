package ru.sergey_gusarov.hw2.exception;

public class BizLogicException extends Exception {
    public BizLogicException(String message) {
        super(message);
    }

    public BizLogicException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public void printMessage() {
        System.err.println(this.getMessage());
    }
}
