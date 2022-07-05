package com.teaminvincible.ESchool.UserModule.Service;

import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.UserModule.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByEmail(String email) throws CustomException;

    Boolean isUserExistWithEmail(String email);

    User saveNewUser(User user) throws CustomException;
}
