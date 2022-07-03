package com.teaminvincible.ESchool.ExceptionManagement.Handlers;

import com.teaminvincible.ESchool.Output.ErrorField;
import com.teaminvincible.ESchool.Output.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class EntityException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ErrorField> errorFields = new ArrayList<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> errorFields.add(
                        new ErrorField(
                                ((FieldError)error).getField(),
                                error.getDefaultMessage()
                        )
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                     new Response<>(
                             HttpStatus.BAD_REQUEST,
                             false,
                             "You need to fill up all fields correctly!",
                             errorFields
                     )
                );
    }
}
