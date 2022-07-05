package com.teaminvincible.ESchool.Configurations;


public class AppConstants {

    public static final String JWT_SECRET = "e_school_javafest";
    public static final long JWT_Expiration = 1296000;

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
