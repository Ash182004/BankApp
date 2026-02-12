package com.Bank.controller;

import java.time.Instant;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.Bank.exception.AccountNotFoundException;


@RestControllerAdvice
public class BankAccountExceptionHandlerController {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ProblemDetail> handle404(AccountNotFoundException e) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Account Not Found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errorCode", "ACCOUNT_NOT_FOUND");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(problemDetail);
    }
}
