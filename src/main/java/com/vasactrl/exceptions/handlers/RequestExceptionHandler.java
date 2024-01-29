package com.vasactrl.exceptions.handlers;

import com.vasactrl.dtos.RequestResponse;
import com.vasactrl.exceptions.BranchNotFoundException;
import com.vasactrl.exceptions.DepartmentNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.AccountNotFoundException;

@RestControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<RequestResponse<String>> handleAccountNotFoundException(AccountNotFoundException ex){
        return ResponseEntity.status(404).body(new RequestResponse<String>("", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<RequestResponse<String>> handleDuplicateKeyException(DuplicateKeyException ex){
        return ResponseEntity.status(404).body(new RequestResponse<String>("", ex.getMessage()));
    }

    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<RequestResponse<String>> handleBranchNotFoundException(BranchNotFoundException ex){
        return ResponseEntity.status(404).body(new RequestResponse<>("", ex.getMessage()));
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<RequestResponse<String>> handleDepartmentNotFoundException(DepartmentNotFoundException ex){
        return ResponseEntity.status(404).body(new RequestResponse<>("", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RequestResponse<String>> handleRuntimeException(RuntimeException ex){
        return ResponseEntity.status(404).body(new RequestResponse<>("", ex.getMessage()));
    }


}
