package com.devwell.dscatalog.resources.exceptions;

import com.devwell.dscatalog.services.exceptions.DatabaseException;
import com.devwell.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
//isso que permite que a classe intercepte alguma a~ção no resource
public class ResourceExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandarError> entityNotFound(ResourceNotFoundException e, HttpServletRequest  request) {
       StandarError error = new StandarError();
       error.setTimestamp(Instant.now());
       error.setStatus(HttpStatus.NOT_FOUND.value());
       error.setError("Resource not found");
       error.setMessage(e.getMessage());
       error.setPath(request.getRequestURI());
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandarError> database(DatabaseException e, HttpServletRequest  request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandarError error = new StandarError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Database exception");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

}
