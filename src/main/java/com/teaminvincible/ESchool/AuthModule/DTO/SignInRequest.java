package com.teaminvincible.ESchool.AuthModule.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignInRequest {

    @NotNull(message = "Must have to provide a valid Email Address or Phone Number.")
    private String email;

    @Size(message = "Password length should be 6(Six).")
    private String password;

    public SignInRequest() {
    }

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
