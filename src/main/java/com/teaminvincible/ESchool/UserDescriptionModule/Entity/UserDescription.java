package com.teaminvincible.ESchool.UserDescriptionModule.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.TaskModule.Entity.Task;
import com.teaminvincible.ESchool.UserModule.Entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users_description")
public class UserDescription implements Serializable {

    @Id
    private String userId;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Role role;

    /**
     *  User Image field should be added
     */


    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH})
    @JoinTable(
            name = "user_course",
            joinColumns = {
                    @JoinColumn(name = "userId")
            },
            inverseJoinColumns ={
                    @JoinColumn(name = "courseId")
            }
    )
    @JsonIgnoreProperties(value = {"courseOwner","students","tasks","meetings"})
    private Set<Course> courses = new HashSet<>();


    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name = "user_task",
            joinColumns = {
                    @JoinColumn(name = "userId")
            },
            inverseJoinColumns ={
                    @JoinColumn(name = "taskId")
            }
    )
    @JsonIgnoreProperties(value = {"users","createdByTeacher","course"})
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name = "user_meeting",
            joinColumns = {
                    @JoinColumn(name = "userId")
            },
            inverseJoinColumns ={
                    @JoinColumn(name = "meetingId")
            }
    )
    @JsonIgnoreProperties(value = {"users","createdBy","course"})
    private Set<Meeting> meetings = new HashSet<>();

    public UserDescription() {
    }

    public UserDescription(User user) {
        this.setRole(user.getRole());
    }

    public UserDescription(String userId, String name, Role role, Set<Course> courses, Set<Task> tasks, Set<Meeting> meetings) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.courses = courses;
        this.tasks = tasks;
        this.meetings = meetings;
    }

    @PreRemove
    public void beforeRemovingEntity(){
        this.courses = new HashSet<>();
        this.tasks = new HashSet<>();
        this.meetings = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDescription)) return false;
        UserDescription that = (UserDescription) o;
        return getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
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

    public void removeCourse(Course course){
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    public void removeMeetingFromUser(Meeting meeting){
        this.meetings.remove(meeting);
        meeting.getUsers().remove(this);
    }

    public Boolean checkIfUserAlreadyEnrolledThisCourse(Course course){
        return this.getCourses().contains(course);
    }

    @Override
    public String toString() {
        return "UserDescription{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", courses=" + courses +
                ", tasks=" + tasks +
                ", meetings=" + meetings +
                '}';
    }
}
