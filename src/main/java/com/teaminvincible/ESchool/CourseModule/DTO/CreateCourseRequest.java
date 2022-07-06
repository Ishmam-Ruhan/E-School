package com.teaminvincible.ESchool.CourseModule.DTO;

import javax.validation.constraints.Size;

public class CreateCourseRequest {
    @Size(min = 1,message = "Course name can't be empty!")
    private String courseTitle;
    @Size(min = 1,message = "Course subtitle can't be empty!")
    private String courseSubTitle;

    public CreateCourseRequest() {
    }

    public CreateCourseRequest(String courseTitle, String courseSubTitle) {
        this.courseTitle = courseTitle;
        this.courseSubTitle = courseSubTitle;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseSubTitle() {
        return courseSubTitle;
    }

    public void setCourseSubTitle(String courseSubTitle) {
        this.courseSubTitle = courseSubTitle;
    }
}
