package com.auth.statefull.cookies_auth.Config;

import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth.statefull.cookies_auth.Config.Exceptions.InvalidRoleException;
import com.auth.statefull.cookies_auth.Config.Exceptions.RoleNotFoundException;
import com.auth.statefull.cookies_auth.Config.Exceptions.UsernameTakenException;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<?> handleUsernameTaken(UsernameTakenException ex) {
        return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<?> handleInvalidRole(InvalidRoleException ex){
        return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleRoleNotFound(RoleNotFoundException ex){
        return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(Map.of("error", ex.getMessage()));
    }

}
