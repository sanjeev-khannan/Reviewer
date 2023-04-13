package com.reviewer.controllers;

import org.hibernate.boot.MappingNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ReviewerErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
        // logic to handle the error and generate an error response
    	Exception ex = new MappingNotFoundException("The requested resource was not found", null);
    	ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, "The requested resource was not found").build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    public String getErrorPath() {
        return ERROR_PATH;
    }
}
