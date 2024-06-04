package me.approximations.music.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdviceErrorHandler {
    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<Map<String, Object>> restClientExceptionHandler(HttpServletRequest request, RestClientResponseException e) {
        final Map<String, Object> map = new LinkedHashMap<>();

        map.put("timestamp", new Date());
        map.put("status", e.getStatusCode().value());
        map.put("error", e.getStatusText());
        map.put("message", e.getMessage());
        map.put("path", request.getRequestURI());

        return ResponseEntity.status(e.getStatusCode()).body(map);
    }
}
