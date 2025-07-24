package com.ecommerce.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiResponse> resoureNotFound(ResourceNotFoundException ex){
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(),false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String,String>> methodArgNotValid(MethodArgumentNotValidException e){
        HashMap<String,String> hashMap=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String errorName=error.getDefaultMessage();
            hashMap.put(fieldName,errorName);
        });
        return new ResponseEntity<>(hashMap,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> disableException(BadCredentialsException e){
        String message="Invalid username or password";
        return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.NOT_FOUND);
    }


}
