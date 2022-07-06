package com.teaminvincible.ESchool.AuthModule.ServiceImplementation;

import com.teaminvincible.ESchool.AuthModule.DTO.CreateUserRequest;
import com.teaminvincible.ESchool.AuthModule.DTO.SignInRequest;
import com.teaminvincible.ESchool.AuthModule.Service.AuthService;
import com.teaminvincible.ESchool.AuthModule.Service.Security.Services.CustomUserDetails;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.AuthModule.Service.Security.JWT.JwtGenerator;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserModule.Entity.User;
import com.teaminvincible.ESchool.UserModule.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AuthServiceImplementation implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    @Autowired
    public AuthServiceImplementation(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public String registration(CreateUserRequest userRequest) throws CustomException {

        if(userService.isUserExistWithEmail(userRequest.getEmail()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "User already exist with email: "+userRequest.getEmail());

        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setRole(userRequest.getRole());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setPhone(userRequest.getPhone());

        UserDescription newUserDescription = new UserDescription(newUser);
        newUserDescription.setName(userRequest.getName());

        newUser.setUserDescription(newUserDescription);

        userService.saveNewUser(newUser);

        return "Registration Completed Successfully!";
    }

    @Override
    public String signIn(SignInRequest signInRequest) throws CustomException {
        if(!userService.isUserExistWithEmail(signInRequest.getEmail()))
            throw new CustomException(HttpStatus.FORBIDDEN, "No user exist with email: "+signInRequest.getEmail());


        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
        );

        if(authentication == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, "Unknown error occured while signing in. Please try again.");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

        String jwt = jwtGenerator.generateToken(userDetails);

        if(jwt == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, "Something went wrong! Please try again.");

        return "Your token: "+jwt;
    }
}
