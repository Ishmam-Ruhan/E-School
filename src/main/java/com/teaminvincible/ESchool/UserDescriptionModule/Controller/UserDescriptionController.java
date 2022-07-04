package com.teaminvincible.ESchool.UserDescriptionModule.Controller;

import com.teaminvincible.ESchool.Annotations.GetAPI;
import com.teaminvincible.ESchool.Annotations.PostAPI;
import com.teaminvincible.ESchool.Output.Response;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Service.UserDescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user-description-management")
public class UserDescriptionController {

    @Autowired
    private UserDescriptionService userDescriptionService;

    @Operation(
            summary = "Update existing user's description"
    )
    @PostAPI("/update-description")
    public ResponseEntity updateUserDescription(@RequestBody UserDescription userDescription){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                   HttpStatus.OK,
                   true,
                   "User Description Updated with ID: "+userDescription.getUserId(),
                   userDescriptionService.updateUserDescription(userDescription)
                ));
    }

    @Operation(
            summary = "Get a user's description by user-id"
    )
    @GetAPI("/get-description/user/{userId}")
    public ResponseEntity getUserDescription(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user description with ID: "+userId,
                        userDescriptionService.getUserDescription(userId)
                ));
    }

    @Operation(
            summary = "Get a user's role by user-id"
    )
    @GetAPI("/get-role/user/{userId}")
    public ResponseEntity getRoleFromUserDescription(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user's role with ID: "+userId,
                        userDescriptionService.getRoleFromUserDescription(userId)
                ));
    }

    @Operation(
            summary = "Get courses by user-id"
    )
    @GetAPI("/get-course/user/{userId}")
    public ResponseEntity getCoursesOfAUser(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user's courses with ID: "+userId,
                        userDescriptionService.getCoursesOfAUser(userId)
                ));
    }

    @Operation(
            summary = "Get tasks by user-id"
    )
    @GetAPI("/get-task/user/{userId}")
    public ResponseEntity getTasksOfAUser(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user's tasks with ID: "+userId,
                        userDescriptionService.getTasksOfUser(userId)
                ));
    }

    @Operation(
            summary = "Get meetings by user-id"
    )
    @GetAPI("/get-meeting/user/{userId}")
    public ResponseEntity getMeetingsOfAUser(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user's meetings with ID: "+userId,
                        userDescriptionService.getMeetingsOfUser(userId)
                ));
    }
}

