package com.teaminvincible.ESchool.Configurations.Master;

import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.Security.Services.CustomUserDetails;
import com.teaminvincible.ESchool.UserModule.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public final class CurrentUser {

    private final UserService userService;

    @Autowired
    public CurrentUser(UserService userService) {
        this.userService = userService;
    }


    public String getCurrentUserId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){

            Object principal = authentication.getPrincipal();

            if(principal instanceof CustomUserDetails){

                String userEmail = ((CustomUserDetails)principal).getEmail();

                String userId = userService.getUserByEmail(userEmail).getUserId();

                return userId;
            }else{
                throw new CustomException(HttpStatus.BAD_REQUEST, "No Active User Present!");
            }

        }
        throw new CustomException(HttpStatus.BAD_REQUEST, "No Active User Present!");
    }

}
