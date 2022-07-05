package com.teaminvincible.ESchool.AuthModule.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teaminvincible.ESchool.Enums.Role;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequest {

    @Column(nullable = false)
    @Size(min = 2, max = 20, message = "Minimum name length is 2 (Two)")
    private String name;

    @Email(message = "Please provide a valid email address!")
    @Column(nullable = false)
    private String email;

    @NotNull(message = "Please provide a valid phone number.")
    @Column(nullable = false)
    private String phone;

    @Size(min = 6, message = "Minimum password length is 6 (Six)")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Please provide your role!")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private Role role;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name, String email, String phone, String password, Role role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
