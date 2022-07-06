package com.teaminvincible.ESchool.CourseModule.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.TaskModule.Entity.Task;
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
@Table(name = "courses")
@JsonIgnoreProperties(value = {"courseJoiningCode","createdDate"}, allowGetters = true)
public class Course implements Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36,columnDefinition = "VARCHAR(255)", nullable = false, updatable = false)
    private String courseId;

    @Column(nullable = false, unique = true)
    private String courseJoiningCode;

    private String courseTitle;

    private String courseSubTitle;

    /**
     *  Course Image should be added
     */

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "courseOwnerId")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings","role"})
    private UserDescription courseOwner;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings","role"})
    private Set<UserDescription> students = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = {CascadeType.MERGE,CascadeType.PERSIST}, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"users", "createdByTeacher","course"})
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = {CascadeType.MERGE,CascadeType.PERSIST}, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"participants", "createdBy","course","meetingAgenda","meetingDescription","meetingAvailableLink"})
    private Set<Meeting> meetings = new HashSet<>();

    private Boolean isClosed = false;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false,updatable = false)
    private Date createdDate;

    public Course() {
    }

    public Course(String courseId, String courseJoiningCode, String courseTitle, String courseSubTitle, UserDescription courseOwner, Set<UserDescription> students, Set<Task> tasks, Set<Meeting> meetings, Boolean isClosed, Date createdDate) {
        this.courseId = courseId;
        this.courseJoiningCode = courseJoiningCode;
        this.courseTitle = courseTitle;
        this.courseSubTitle = courseSubTitle;
        this.courseOwner = courseOwner;
        this.students = students;
        this.tasks = tasks;
        this.meetings = meetings;
        this.isClosed = isClosed;
        this.createdDate = createdDate;
    }

    @PreRemove
    public void beforeRemovingEntity(){
        this.courseOwner = null;
        this.students.forEach(student -> {
            student.removeCourse(this);
        });
//        this.tasks.forEach(task -> {
//            task.removeCourse(this);
//        });
        this.meetings = new HashSet<>();
    }

    public void removeUserDescription(UserDescription userDescription){
        this.students.remove(userDescription);
        userDescription.getCourses().remove(this);
    }

    public void removeTasks(Task task){
        this.tasks.remove(task);
    }

    public void removeMeetings(Meeting meeting){
        this.meetings.remove(meeting);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getCourseId().equals(course.getCourseId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId());
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseJoiningCode() {
        return courseJoiningCode;
    }

    public void setCourseJoiningCode(String courseJoiningCode) {
        this.courseJoiningCode = courseJoiningCode;
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

    public UserDescription getCourseOwner() {
        return courseOwner;
    }

    public void setCourseOwner(UserDescription courseOwner) {
        this.courseOwner = courseOwner;
    }

    public Set<UserDescription> getStudents() {
        return students;
    }

    public void setStudents(Set<UserDescription> students) {
        this.students = students;
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

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}