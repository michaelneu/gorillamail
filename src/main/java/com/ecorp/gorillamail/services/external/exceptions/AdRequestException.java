package com.ecorp.gorillamail.services.external.exceptions;

public class AdRequestException extends Exception {
    public AdRequestException(Throwable cause) {
        super(cause);
    }
    
    public AdRequestException(String message) {
        super(message);
    }
}
