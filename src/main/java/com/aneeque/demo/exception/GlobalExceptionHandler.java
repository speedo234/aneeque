package com.aneeque.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException exception, HttpServletResponse response, WebRequest request) throws IOException{
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpServletResponse.SC_BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), exception.getMessage(), request.getDescription(false));
        return ResponseEntity.ok(errorDetails);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(ApplicationException exception, HttpServletResponse response, WebRequest request) throws IOException{
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpServletResponse.SC_NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE.toString(), exception.getMessage(), request.getDescription(false));
        return ResponseEntity.ok(errorDetails);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletResponse response, WebRequest request) throws IOException{
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpServletResponse.SC_NOT_FOUND, HttpStatus.NOT_FOUND.toString(), exception.getMessage(), request.getDescription(false));
        return ResponseEntity.ok(errorDetails);
    }

}
