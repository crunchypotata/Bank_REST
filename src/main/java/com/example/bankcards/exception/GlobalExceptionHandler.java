package com.example.bankcards.exception;

import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<Problem> handleCardNotFound(CardNotFoundException ex) {
        Problem problem = Problem.builder()
                .withTitle("Card not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(ex.getMessage())
                .build();
        return ResponseEntity.status(Status.NOT_FOUND.getStatusCode()).body(problem);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Problem> handleUserNotFound(UserNotFoundException ex) {
        Problem problem = Problem.builder()
                .withTitle("User not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(ex.getMessage())
                .build();
        return ResponseEntity.status(Status.NOT_FOUND.getStatusCode()).body(problem);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Problem> handleUserAlreadyExist(UserAlreadyExistException ex) {
        Problem problem = Problem.builder()
                .withTitle("Пользователь уже существует")
                .withStatus(Status.CONFLICT)
                .withDetail(ex.getMessage())
                .build();
        return ResponseEntity.status(Status.CONFLICT.getStatusCode()).body(problem);
    }

    @ExceptionHandler(CardOperationException.class)
    public ResponseEntity<Problem> handleCardOperation(CardOperationException ex) {
        Problem problem = Problem.builder()
                .withTitle("Error during card operation")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(ex.getMessage())
                .build();
        return ResponseEntity.status(Status.BAD_REQUEST.getStatusCode()).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handleOther(Exception ex) {
        Problem problem = Problem.builder()
                .withTitle("Internal Server Error")
                .withStatus(Status.INTERNAL_SERVER_ERROR)
                .withDetail(ex.getMessage())
                .build();
        return ResponseEntity.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handleAnyException(Exception ex) {
        ex.printStackTrace();
        Problem problem = Problem.builder()
                .withTitle("Internal Server Error")
                .withStatus(Status.INTERNAL_SERVER_ERROR)
                .withDetail(ex.getMessage())
                .build();
        return ResponseEntity.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).body(problem);
    }

}

