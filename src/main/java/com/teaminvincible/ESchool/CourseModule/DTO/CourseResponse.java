package com.teaminvincible.ESchool.CourseModule.DTO;

import java.util.Date;

public class CourseResponse {
    private String courseId;
    private String courseTitle;
    private String courseSubTitle;
    private Date createdDate;

    public CourseResponse() {
    }

    public CourseResponse(String courseId, String courseTitle, String courseSubTitle, Date createdDate) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseSubTitle = courseSubTitle;
        this.createdDate = createdDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
