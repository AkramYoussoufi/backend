package com.project.tklembackend.config;

import jakarta.mail.AuthenticationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.InstanceAlreadyExistsException;
import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j

public class GlobalExceptionHandler {
    @ExceptionHandler({AuthenticationFailedException.class, NoSuchElementException.class, DisabledException.class, InstanceAlreadyExistsException.class, RequestRejectedException.class})
    public ResponseEntity<Object> handleGlobalException(Exception e) {
        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
}
