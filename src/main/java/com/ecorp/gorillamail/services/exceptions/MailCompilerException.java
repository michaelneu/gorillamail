package com.ecorp.gorillamail.services.exceptions;

public class MailCompilerException extends MailException {
    public MailCompilerException(String message) {
        super(message);
    }

    public MailCompilerException(String message, Throwable cause) {
        super(message, cause);
    }
}
