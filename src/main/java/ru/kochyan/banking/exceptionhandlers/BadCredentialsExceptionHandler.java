package ru.kochyan.banking.exceptionhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kochyan.banking.entities.User;
import ru.kochyan.banking.enums.LogLevel;
import ru.kochyan.banking.facades.LogFacade;

@RestControllerAdvice
public class BadCredentialsExceptionHandler {

    private final LogFacade logFacade;

    @Autowired
    public BadCredentialsExceptionHandler(LogFacade logFacade) {
        this.logFacade = logFacade;
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<String> handleException(BadCredentialsException e) {
        logFacade.write(e.getMessage(), LogLevel.WARN);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
