package com.teaminvincible.ESchool.UserDescriptionModule.DTO;

import com.teaminvincible.ESchool.CourseModule.DTO.CourseResponse;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingResponse;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.TaskModule.DTO.TaskResponse;
import com.teaminvincible.ESchool.TaskModule.Entity.Task;
import com.teaminvincible.ESchool.UserModule.Entity.User;

import java.util.Set;

public class UserDescriptionResponse {

    private String userId;
    private String name;

    public Role role;

    private Set<CourseResponse> courses;

    private Set<TaskResponse> tasks;

    private Set<MeetingResponse> meetings;

    public UserDescriptionResponse() {
    }

    public UserDescriptionResponse(String userId, String name, Role role,  Set<CourseResponse> courses, Set<TaskResponse> tasks, Set<MeetingResponse> meetings) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.courses = courses;
        this.tasks = tasks;
        this.meetings = meetings;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<CourseResponse> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseResponse> courses) {
        this.courses = courses;
    }

    public Set<TaskResponse> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskResponse> tasks) {
        this.tasks = tasks;
    }

    public Set<MeetingResponse> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<MeetingResponse> meetings) {
        this.meetings = meetings;
    }

}
