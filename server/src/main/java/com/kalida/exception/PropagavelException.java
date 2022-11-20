package com.kalida.exception;

public class PropagavelException extends Exception {

    private final String infoRequest;

    public PropagavelException(String message) {
        this(message, null);
    }

    public PropagavelException(String message, String requestInfo) {
        super(message);
        this.infoRequest = requestInfo;
    }

    public String getInfoRequest() {
        return infoRequest;
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
