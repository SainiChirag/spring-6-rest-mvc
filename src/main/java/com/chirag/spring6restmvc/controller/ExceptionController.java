package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.exception.UnknownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<String> handleUnknownException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // this method would handle the exception thrown in case of validation failures
    // and would get the reason of the validation failure and set that to the response body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.debug("Validation exception");
        List<Map<String,String>>
                errorList = ex.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String,String> errorMap= new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();
        //return ResponseEntity.badRequest().body(ex.getBindingResult().getFieldError());
        return ResponseEntity.badRequest().body(errorList);
    }
}
