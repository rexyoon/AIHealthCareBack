package com.aihealthcare.aihealthcare.exception;

import com.aihealthcare.aihealthcare.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustom(CustomException e){
        ErrorCode code = e.getErrorCode();
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.fail(code.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e){
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.fail("Server Error"));
    }
}