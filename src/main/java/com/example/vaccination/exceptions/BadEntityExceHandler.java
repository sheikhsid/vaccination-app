package com.example.vaccination.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;



@ControllerAdvice
public class BadEntityExceHandler extends ResponseEntityExceptionHandler {

    
    @ExceptionHandler(PtNotFoundExce.class)
    protected final ResponseEntity<Object> handleUserNotFoundException(PtNotFoundExce exception){
        var response = new PtNotFoundExceRes(exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    
}
