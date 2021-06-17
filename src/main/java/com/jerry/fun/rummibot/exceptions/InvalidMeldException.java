package com.jerry.fun.rummibot.exceptions;

public class InvalidMeldException extends RuntimeException {
    public InvalidMeldException() {
        super("The tiles set cannot form a meld.");
    }

    public InvalidMeldException(String message) {
        super(message);
    }
}
