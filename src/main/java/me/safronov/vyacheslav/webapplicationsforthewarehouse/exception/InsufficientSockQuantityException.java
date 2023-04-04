package me.safronov.vyacheslav.webapplicationsforthewarehouse.exception;

public class InsufficientSockQuantityException extends RuntimeException {
    public InsufficientSockQuantityException(String message) {
        super(message);
    }
}
