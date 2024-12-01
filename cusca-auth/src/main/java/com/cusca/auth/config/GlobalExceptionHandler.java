package com.cusca.auth.config;

import com.cusca.auth.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ErrorResponseDto(ex.getMessage(), ex.getHttpStatus().value()));
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "Fatal Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleHttpClientErrorException(HttpClientErrorException ex) {
        int statusCode = ex.getStatusCode().value();
        String message = ex.getMessage();
        ErrorResponseDto errorResponse = new ErrorResponseDto(message, statusCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(statusCode));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "Access Denied.",
                HttpStatus.FORBIDDEN.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "The requested path was not found. Please check the URL.",
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}