package com.teaminvincible.ESchool.AuthModule.Controller;

import com.teaminvincible.ESchool.Annotations.PostAPI;
import com.teaminvincible.ESchool.AuthModule.DTO.CreateUserRequest;
import com.teaminvincible.ESchool.AuthModule.DTO.SignInRequest;
import com.teaminvincible.ESchool.AuthModule.Service.AuthService;
import com.teaminvincible.ESchool.Output.Response;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth-management")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Registration Request"
    )
    @PostAPI("/registration")
    public ResponseEntity userRegistration(@Valid @RequestBody CreateUserRequest userRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                   HttpStatus.OK,
                        true,
                        "Registration Success!",
                        authService.registration(userRequest)
                ));
    }

    @Operation(
            summary = "Sign In Request"
    )
    @PostAPI("/sign-in")
    public ResponseEntity userSignIn(@Valid @RequestBody SignInRequest userRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Sign In Success!",
                        "Token: "+authService.signIn(userRequest)
                ));
    }

}
