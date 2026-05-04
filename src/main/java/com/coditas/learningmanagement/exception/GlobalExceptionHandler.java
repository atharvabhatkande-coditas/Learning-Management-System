package com.coditas.learningmanagement.exception;

import com.coditas.learningmanagement.dto.response.ApplicationResponse;
import com.coditas.learningmanagement.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>>handleNotFoundException(NotFoundException e){
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        ApplicationResponse<List<ErrorResponse>>applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
        return new ResponseEntity<>(applicationResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>>handleAlreadyExistException(AlreadyExistException e){
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT.value());
        ApplicationResponse<List<ErrorResponse>>applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
        return new ResponseEntity<>(applicationResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleValidationException(MethodArgumentNotValidException e){
        Map<String,String> errors=new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>>handleAuthorizationException(AuthorizationException e){
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value());
        ApplicationResponse<List<ErrorResponse>>applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
        return new ResponseEntity<>(applicationResponse,HttpStatus.UNAUTHORIZED);
    }




}
