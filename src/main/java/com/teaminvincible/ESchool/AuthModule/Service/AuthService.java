package com.teaminvincible.ESchool.AuthModule.Service;

import com.teaminvincible.ESchool.AuthModule.DTO.CreateUserRequest;
import com.teaminvincible.ESchool.AuthModule.DTO.SignInRequest;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String registration(CreateUserRequest userRequest) throws CustomException;

    String signIn(SignInRequest signInRequest) throws CustomException;

}
