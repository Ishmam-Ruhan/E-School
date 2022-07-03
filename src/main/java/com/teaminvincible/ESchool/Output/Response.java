package com.teaminvincible.ESchool.Output;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
    private HttpStatus httpStatus;
    private boolean isExecute;
    private String serverMessage;
    private T data;
    private List<ErrorField> errorFields = new ArrayList<>();

    public Response() {
    }

    public Response(HttpStatus httpStatus, boolean isExecute, String serverMessage, T data) {
        this.httpStatus = httpStatus;
        this.isExecute = isExecute;
        this.serverMessage = serverMessage;
        this.data = data;
    }

    public Response(HttpStatus httpStatus, boolean isExecute, String serverMessage) {
        this.httpStatus = httpStatus;
        this.isExecute = isExecute;
        this.serverMessage = serverMessage;
    }

    public Response(HttpStatus httpStatus, boolean isExecute, String serverMessage, List<ErrorField> errorFields) {
        this.httpStatus = httpStatus;
        this.isExecute = isExecute;
        this.serverMessage = serverMessage;
        this.errorFields = errorFields;
    }



    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public boolean isExecute() {
        return isExecute;
    }

    public void setExecute(boolean execute) {
        isExecute = execute;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorField> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(List<ErrorField> errorFields) {
        this.errorFields = errorFields;
    }
}
