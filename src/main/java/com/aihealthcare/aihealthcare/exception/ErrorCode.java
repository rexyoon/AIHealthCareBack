package com.aihealthcare.aihealthcare.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    HEALTH_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "HEALTH_RECORD_NOT_FOUND", "해당 건강 기록을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
    public String getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }
}