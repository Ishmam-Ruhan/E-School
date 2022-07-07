package com.teaminvincible.ESchool.ExceptionManagement.Handlers;


import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.Output.Response;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@ControllerAdvice
public class OtherException{

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response> handleCustomException(CustomException customException){
        return ResponseEntity
                .status(customException.getHttpStatus())
                .body(new Response<>(
                   customException.getHttpStatus(),
                   false,
                   customException.getExceptionMessage()
                ));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Response> jwtTokeExpiration(JwtException jwtException){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new Response<>(
                        HttpStatus.FORBIDDEN,
                        false,
                        jwtException.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex,
                                         HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof NullPointerException) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(
                            HttpStatus.BAD_REQUEST,
                            false,
                            ex.getMessage()
                    ));
        }

        if(response.getStatus() == 403){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Response<>(
                            HttpStatus.FORBIDDEN,
                            false,
                            ex.getMessage()
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response<>(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        false,
                        ex.getMessage()
                ));
    }

}
