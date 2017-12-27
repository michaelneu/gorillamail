package com.ecorp.gorillamail.services.exceptions;

public abstract class MailException extends Exception {
    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }
}
