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
    @Column(unique = true,length = 36,columnDefinition = "VARCHAR(255)", nullable = false, updatable = false)
    private String taskId;

    private String taskTitle;

    private String taskDetails;

    /**
     *  Task Images should be added
     */

    @ManyToMany(mappedBy = "tasks")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings"})
    private Set<UserDescription> assignedStudents = new HashSet<>();

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "taskOwnerId")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings","role"})
    private UserDescription createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties(value = {"courseOwner","students","tasks","meetings","courseJoiningCode","courseSubTitle","createdDate"})
    private Course course;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date dueDate;

    private Boolean isClosed;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(nullable = false,updatable = false)
    private Date assignedOn;

    public Task() {
    }

    public Task(String taskId, String taskTitle, String taskDetails, Set<UserDescription> assignedStudents, UserDescription createdBy, Course course, Date dueDate, Boolean isClosed, Date assignedOn) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDetails = taskDetails;
        this.assignedStudents = assignedStudents;
        this.createdBy = createdBy;
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

    @PreRemove
    public void beforeRemovingEntity(){
        this.assignedStudents.forEach(userDescription -> {
            userDescription.removeTaskFromUser(this);
        });
        this.createdBy  = null;
        this.course = null;
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

    public Set<UserDescription> getAssignedStudents() {
        return assignedStudents;
    }

    public void setAssignedStudents(Set<UserDescription> assignedStudents) {
        this.assignedStudents = assignedStudents;
    }

    public UserDescription getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDescription createdByTeacher) {
        this.createdBy = createdByTeacher;
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

}
