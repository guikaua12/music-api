package me.approximations.music.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="")
public class NotFoundException extends HttpClientErrorException {
    public NotFoundException(String statusText) {
        super(HttpStatus.NOT_FOUND, statusText);
    }
}
