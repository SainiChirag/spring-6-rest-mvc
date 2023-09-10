package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.exception.UnknownException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<String> handleUnknownException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
