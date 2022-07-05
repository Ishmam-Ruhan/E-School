package com.teaminvincible.ESchool.CourseModule.Controller;

import com.teaminvincible.ESchool.Annotations.DeleteAPI;
import com.teaminvincible.ESchool.Annotations.GetAPI;
import com.teaminvincible.ESchool.Annotations.PostAPI;
import com.teaminvincible.ESchool.Annotations.PutAPI;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.Output.Response;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostAPI("/create-course")
    public ResponseEntity createCourse(@RequestBody Course course){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(
                   HttpStatus.CREATED,
                   true,
                   "Course created successfully.",
                   courseService.createCourse(course.getCourseOwner().getUserId(), course)
                ));
    }

    @Operation(
            summary = "Update a course."
    )
    @PutAPI("/update-course")
    public ResponseEntity updateCourse(@RequestBody Course course){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Course updated successfully.",
                        courseService.updateCourse(course.getCourseOwner().getUserId(), course)
                ));
    }

    @Operation(
            summary = "Enroll a course."
    )
    @PutAPI("/join-course/code/{joiningCode}/user/{userId}")
    public ResponseEntity joinCourseByJoiningCode(@PathVariable String joiningCode, @PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Joined Course successfully.",
                        courseService.joinCourse(userId,joiningCode)
                ));
    }

    @Operation(
            summary = "Un-Enroll from a course."
    )
    @PutAPI("/remove-course/course/{courseId}/user/{userId}")
    public ResponseEntity unEnrollCourse(@PathVariable String courseId, @PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Un-Enrolled Course successfully.",
                        courseService.unEnrollFromACourse(userId,courseId)
                ));
    }

    @Operation(
            summary = "Delete a course."
    )
    @DeleteAPI("/delete-course/user/{userId}/course/{courseId}")
    public ResponseEntity deleteCourse(@PathVariable String userId ,@PathVariable String courseId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Course deleted successfully.",
                        courseService.deleteCourse(userId, courseId)
                ));
    }

    @Operation(
            summary = "Get all courses of a user"
    )
    @GetAPI("/get-course/user/role/teacher/{teacherId}")
    public ResponseEntity getAllCourseOfATeacher(@PathVariable String teacherId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "All courses of user with id: "+teacherId+" fetched successfully!",
                        courseService.getAllCourseOfATeacher(teacherId)
                ));
    }

}
