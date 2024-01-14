package ru.clevertec.house.exception;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
        ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            "Requested resource not found (uuid = " + ex.getUuid() + ")", 404);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex) {
        String messageArgument = (String) Objects.requireNonNull(ex.getDetailMessageArguments())[1];
        ErrorResponse errorResponse = new ErrorResponse(
            messageArgument, 400);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
