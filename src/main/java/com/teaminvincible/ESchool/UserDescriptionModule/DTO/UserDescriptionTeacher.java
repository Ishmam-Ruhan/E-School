package com.teaminvincible.ESchool.UserDescriptionModule.DTO;

import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.TaskModule.Entity.Task;
import com.teaminvincible.ESchool.UserModule.Entity.User;

import java.util.HashSet;
import java.util.Set;

public class UserDescriptionTeacher {

    private String userId;

    private String name;

    public Role role;

    private User user;

    private Set<Course> courses = new HashSet<>();


    private Set<Task> tasks = new HashSet<>();

    private Set<Meeting> meetings = new HashSet<>();

    public UserDescriptionTeacher() {
    }

    public UserDescriptionTeacher(String userId, String name, Role role, User user, Set<Course> courses, Set<Task> tasks, Set<Meeting> meetings) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.user = user;
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

    public void setRole() {
        this.role = this.user.getRole();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }



    @Override
    public String toString() {
        return "UserDescription{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", user=" + user +
                ", courses=" + courses +
                ", tasks=" + tasks +
                ", meetings=" + meetings +
                '}';
    }
}
