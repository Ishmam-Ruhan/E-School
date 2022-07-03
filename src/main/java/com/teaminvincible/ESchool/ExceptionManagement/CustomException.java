package com.teaminvincible.ESchool.ExceptionManagement;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    private HttpStatus httpStatus;
    private String exceptionMessage;

    public CustomException(HttpStatus httpStatus, String exceptionMessage) {
        this.httpStatus = httpStatus;
        this.exceptionMessage = exceptionMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
