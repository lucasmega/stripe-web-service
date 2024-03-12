package com.cutconnect.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("BUSINESS_EXCEPTION", ex.getMessage()));
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorResponse> hadleInternalServerError(InternalServerError e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage()));
    }
}
