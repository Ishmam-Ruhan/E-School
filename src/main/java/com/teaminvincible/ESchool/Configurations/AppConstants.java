package com.teaminvincible.ESchool.Configurations;

public class AppConstants {

    public static String[] UNAUTHORIZED_GETWAYS(){

        String[] endPoints = {
                "/documentation/**",
                "/auth-management/**",
                "/swagger-ui/**",
                "/v3/api-docs/**"
        };

        return endPoints;
    }

}
