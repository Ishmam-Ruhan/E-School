package com.teaminvincible.ESchool.AuthModule.Service.Security.Services;

import com.teaminvincible.ESchool.UserModule.Entity.User;
import com.teaminvincible.ESchool.UserModule.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= userService.getUserByEmail(email);

        return new CustomUserDetails(user);
    }
}
