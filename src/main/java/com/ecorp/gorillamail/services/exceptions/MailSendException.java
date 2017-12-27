package com.ecorp.gorillamail.services.exceptions;

public class MailSendException extends MailException {
    public MailSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
