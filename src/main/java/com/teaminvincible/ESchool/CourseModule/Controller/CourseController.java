package com.teaminvincible.ESchool.CourseModule.Controller;

import com.teaminvincible.ESchool.Annotations.DeleteAPI;
import com.teaminvincible.ESchool.Annotations.GetAPI;
import com.teaminvincible.ESchool.Annotations.PostAPI;
import com.teaminvincible.ESchool.Annotations.PutAPI;
import com.teaminvincible.ESchool.CourseModule.DTO.CreateCourseRequest;
import com.teaminvincible.ESchool.CourseModule.DTO.UpdateCourseRequest;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.Output.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/course-management")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Operation(
            summary = "Create a course."
    )
    @Schema(name = "create course", implementation = CreateCourseRequest.class)
    @PostAPI("/create-course")
    public ResponseEntity createCourse(@RequestBody CreateCourseRequest courseRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(
                   HttpStatus.CREATED,
                   true,
                   "Course created successfully.",
                   courseService.createCourse(courseRequest)
                ));
    }

    @Operation(
            summary = "Update a course."
    )
    @PutAPI("/update-course")
    @Schema(name = "update course schema", implementation = UpdateCourseRequest.class)
    public ResponseEntity updateCourse(@RequestBody UpdateCourseRequest updateCourseRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Course updated successfully.",
                        courseService.updateCourse(updateCourseRequest)
                ));
    }

    @Operation(
            summary = "Enroll a course."
    )
    @PutAPI("/join-course")
    public ResponseEntity joinCourseByJoiningCode(@RequestParam String joinCode){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Joined Course successfully.",
                        courseService.joinCourse(joinCode)
                ));
    }

    @Operation(
            summary = "Un-Enroll from a course."
    )
    @DeleteAPI("/unenroll-course")
    public ResponseEntity unEnrollCourse(@RequestParam String courseId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Un-Enrolled Course successfully.",
                        courseService.unEnrollFromACourse(courseId)
                ));
    }

    @Operation(
            summary = "Get all meetings of a enrolled course."
    )
    @GetAPI("/get-meetings")
    public ResponseEntity getMeetings(@RequestParam String courseId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Fetched all meetings successfully.",
                        courseService.getAllMeetingsOfCourse(courseId)
                ));
    }

    @Operation(
            summary = "Get all tasks of a enrolled course."
    )
    @GetAPI("/get-tasks")
    public ResponseEntity getTasks(@RequestParam String courseId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Fetched all taskssuccessfully.",
                        courseService.getAllTasksOfACourse(courseId)
                ));
    }

    @Operation(
            summary = "Delete a course."
    )
    @DeleteAPI("/delete-course")
    public ResponseEntity deleteCourse(@RequestParam String courseId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Course deleted successfully.",
                        courseService.deleteCourse(courseId)
                ));
    }

}
