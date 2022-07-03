package com.teaminvincible.ESchool.TaskModule.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36,columnDefinition = "VARCHAR(255)", nullable = false, updatable = false)
    private String taskId;

    private String taskTitle;

    private String taskDetails;

    /**
     *  Task Image should be added
     */

    @ManyToMany(mappedBy = "tasks")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings"})
    private Set<UserDescription> users = new HashSet<>();

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "courseOwnerId")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings"})
    private UserDescription createdByTeacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties(value = {"courseOwner","students","tasks","meetings"})
    private Course course;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dueDate;

    private Boolean isClosed;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false,updatable = false)
    private Date assignedOn;

    public Task() {
    }

    public Task(String taskId, String taskTitle, String taskDetails, Set<UserDescription> users, UserDescription createdByTeacher, Course course, Date dueDate, Boolean isClosed, Date assignedOn) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDetails = taskDetails;
        this.users = users;
        this.createdByTeacher = createdByTeacher;
        this.course = course;
        this.dueDate = dueDate;
        this.isClosed = isClosed;
        this.assignedOn = assignedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getTaskId().equals(task.getTaskId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskId());
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public Set<UserDescription> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDescription> users) {
        this.users = users;
    }

    public UserDescription getCreatedByTeacher() {
        return createdByTeacher;
    }

    public void setCreatedByTeacher(UserDescription createdByTeacher) {
        this.createdByTeacher = createdByTeacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Date getAssignedOn() {
        return assignedOn;
    }

    public void setAssignedOn(Date assignedOn) {
        this.assignedOn = assignedOn;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDetails='" + taskDetails + '\'' +
                ", users=" + users +
                ", createdByTeacher=" + createdByTeacher +
                ", course=" + course +
                ", dueDate=" + dueDate +
                ", isClosed=" + isClosed +
                ", assignedOn=" + assignedOn +
                '}';
    }
}
