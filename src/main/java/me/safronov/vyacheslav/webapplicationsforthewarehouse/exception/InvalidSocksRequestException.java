package me.safronov.vyacheslav.webapplicationsforthewarehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Invalid socks request")
public class InvalidSocksRequestException extends RuntimeException {
    public InvalidSocksRequestException(String message) {
        super(message);
    }
}
