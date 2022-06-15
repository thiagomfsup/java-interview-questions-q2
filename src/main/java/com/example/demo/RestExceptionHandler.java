package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.MalformedURLException;

/**
 * A {@link RestControllerAdvice}-annotated class responsible to transform an
 * Exception into a {@link ResponseEntity} object.
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * Return <code>HTTP 400 Bad Request</code> for {@link MalformedURLException} exception.
     * @param ex {@link Exception} instance of the original exception
     * @return {@link ResponseEntity} object with <code>Bad Request</code> status.
     */
    @ExceptionHandler({ MalformedURLException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Void> badRequest(Exception ex) {
        log.error("The {} exception has been thrown (message: {}). Responding HTTP 400 Bad Request.", ex.getClass(),
                ex.getMessage());
        return ResponseEntity.badRequest().build();
    }

}
