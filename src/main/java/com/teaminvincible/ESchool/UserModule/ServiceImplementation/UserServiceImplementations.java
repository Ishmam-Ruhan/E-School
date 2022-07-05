package com.teaminvincible.ESchool.UserModule.ServiceImplementation;

import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.UserModule.Entity.User;
import com.teaminvincible.ESchool.UserModule.Repository.UserRepository;
import com.teaminvincible.ESchool.UserModule.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class UserServiceImplementations implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws CustomException {
        Optional<User> user = userRepository.findByemail(email);

        if(user.isPresent())return user.get();

        throw new CustomException(HttpStatus.NOT_FOUND, "Email or Phone Incorrect.");
    }

    @Override
    public Boolean isUserExistWithEmail(String email) {
        Optional<User> user = userRepository.findByemail(email);

        if(user.isPresent())return true;

        return false;
    }

    @Override
    public User saveNewUser(User user) throws CustomException {
        User user1;
        try{
           user1 =  userRepository.save(user);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process your request right now. Please try again.");
        }
        return user1;
    }

}
