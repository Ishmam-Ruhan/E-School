package com.teaminvincible.ESchool.Configurations.Master;

import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Repository.UserDescriptionRepository;
import com.teaminvincible.ESchool.UserModule.Entity.User;
import com.teaminvincible.ESchool.UserModule.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
public class AppInitialization {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDescriptionRepository userDescriptionRepository;

    @PostConstruct
    public void init(){
        //addUserStudent();
        //addUserTeacher();
    }

    void addUserStudent(){
        User user = new User();
        user.setEmail("email@student.com");
        user.setPassword("1234");
        user.setPhone("0987654321");
        user.setRole(Role.STUDENT);

        UserDescription userDescription = new UserDescription(user);
        userDescription.setName("Ishmam");

        user.setUserDescription(userDescription);

        String id = userRepository.save(user).getUserDescription().getUserId();

        Optional<UserDescription> byId = userDescriptionRepository.findByuserId(id);

        if(byId.isPresent()) System.out.println(byId.get());
        else System.out.println("Not found with: "+id);
    }

    void addUserTeacher(){
        User user = new User();
        user.setEmail("hamim@teacher.com");
        user.setPassword("9876");
        user.setPhone("123456789");
        user.setRole(Role.TEACHER);

        UserDescription userDescription = new UserDescription(user);
        userDescription.setName("Hamim");

        user.setUserDescription(userDescription);

        String id = userRepository.save(user).getUserDescription().getUserId();

        Optional<UserDescription> byId = userDescriptionRepository.findByuserId(id);

        if(byId.isPresent()) System.out.println(byId.get());
        else System.out.println("Not found with: "+id);
    }

}
