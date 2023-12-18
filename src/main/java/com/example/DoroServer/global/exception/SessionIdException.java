package com.example.DoroServer.global.exception;

public class SessionIdException extends RuntimeException {

    public SessionIdException(Code code) {
        super(code.name());
    }
}
