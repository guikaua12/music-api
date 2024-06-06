package me.approximations.music.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value=HttpStatus.UNSUPPORTED_MEDIA_TYPE, reason="")
public class UnsupportedFileTypeException extends HttpClientErrorException {
    public UnsupportedFileTypeException(String statusText) {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, statusText);
    }
}
