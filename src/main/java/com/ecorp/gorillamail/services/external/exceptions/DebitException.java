package com.ecorp.gorillamail.services.external.exceptions;

public class DebitException extends Exception {
    public DebitException(Throwable cause) {
        super(cause);
    }
    
    public DebitException(String message) {
        super(message);
    }
}
