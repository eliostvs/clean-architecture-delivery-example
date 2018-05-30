package com.delivery.presenter.rest.api.common;

import com.delivery.core.domain.NotFoundException;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse(false, "Resource not found"), HttpStatus.NOT_FOUND);
    }
}
