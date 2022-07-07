package com.teaminvincible.ESchool.UserDescriptionModule.Controller;

import com.teaminvincible.ESchool.Annotations.GetAPI;
import com.teaminvincible.ESchool.Annotations.PostAPI;
import com.teaminvincible.ESchool.Configurations.Master.CurrentUser;
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

    @Autowired
    private CurrentUser currentUser;

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
            summary = "Get user's description"
    )
    @GetAPI("/get-description")
    public ResponseEntity getUserDescription(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user description",
                        userDescriptionService.getMinimalUserDescription()
                ));
    }

    @Operation(
            summary = "Get user's role"
    )
    @GetAPI("/get-role")
    public ResponseEntity getRoleFromUserDescription(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user's role",
                        userDescriptionService.getRoleFromUserDescription()
                ));
    }

    @Operation(
            summary = "Get All Courses"
    )
    @GetAPI("/get-courses")
    public ResponseEntity getCoursesOfAUser(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user's courses",
                        userDescriptionService.getCoursesOfAUser()
                ));
    }

    @Operation(
            summary = "Get All Tasks"
    )
    @GetAPI("/get-tasks")
    public ResponseEntity getTasksOfAUser(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user's tasks",
                        userDescriptionService.getTasksOfUser()
                ));
    }

    @Operation(
            summary = "Get All Meetings"
    )
    @GetAPI("/get-meeting")
    public ResponseEntity getMeetingsOfAUser(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Successfully fetched user's meetings",
                        userDescriptionService.getMeetingsOfUser()
                ));
    }
}

