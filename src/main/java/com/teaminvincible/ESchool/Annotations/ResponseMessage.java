package com.teaminvincible.ESchool.Annotations;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponse(responseCode = "200", description = "OK", content = @Content)
@ApiResponse(responseCode = "201", description = "CREATED", content = @Content)
@ApiResponse(responseCode = "202", description = "ACCEPTED")
@ApiResponse(responseCode = "204", description = "NO_CONTENT")
@ApiResponse(responseCode = "400", description = "BAD_REQUEST")
@ApiResponse(responseCode = "400", description = "BAD_REQUEST")
@ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
@ApiResponse(responseCode = "403", description = "FORBIDDEN")
@ApiResponse(responseCode = "404", description = "NOT_FOUND")
@ApiResponse(responseCode = "405", description = "METHOD_NOT_ALLOWED")
@ApiResponse(responseCode = "408", description = "REQUEST_TIMEOUT")
@ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")
@ApiResponse(responseCode = "502", description = "BAD_GATEWAY")
@ApiResponse(responseCode = "503", description = "SERVICE_UNAVAILABLE")
public @interface ResponseMessage {
}
