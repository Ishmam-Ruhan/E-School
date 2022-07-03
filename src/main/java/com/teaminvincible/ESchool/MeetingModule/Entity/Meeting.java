package com.teaminvincible.ESchool.MeetingModule.Entity;

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
@Table(name = "meetings")
public class Meeting implements Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36,columnDefinition = "VARCHAR(255)", nullable = false, updatable = false)
    private String meetingId;

    private String meetingTitle;

    private String meetingAgenda;

    private String meetingSchedule;

    @ManyToMany(mappedBy = "meetings")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings"})
    private Set<UserDescription>  users = new HashSet<>();

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "courseOwnerId")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings"})
    private UserDescription createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties(value = {"courseOwner","students","tasks","meetings"})
    private Course course;

    private Boolean isClosed;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false,updatable = false)
    private Date createdAt;

    public Meeting() {
    }

    public Meeting(String meetingId, String meetingTitle, String meetingAgenda, String meetingSchedule, Set<UserDescription> users, UserDescription createdBy, Course course, Boolean isClosed, Date createdAt) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.meetingAgenda = meetingAgenda;
        this.meetingSchedule = meetingSchedule;
        this.users = users;
        this.createdBy = createdBy;
        this.course = course;
        this.isClosed = isClosed;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return getMeetingId().equals(meeting.getMeetingId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMeetingId());
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingAgenda() {
        return meetingAgenda;
    }

    public void setMeetingAgenda(String meetingAgenda) {
        this.meetingAgenda = meetingAgenda;
    }

    public String getMeetingSchedule() {
        return meetingSchedule;
    }

    public void setMeetingSchedule(String meetingSchedule) {
        this.meetingSchedule = meetingSchedule;
    }

    public Set<UserDescription> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDescription> users) {
        this.users = users;
    }

    public UserDescription getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDescription createdBy) {
        this.createdBy = createdBy;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetingId='" + meetingId + '\'' +
                ", meetingTitle='" + meetingTitle + '\'' +
                ", meetingAgenda='" + meetingAgenda + '\'' +
                ", meetingSchedule='" + meetingSchedule + '\'' +
                ", users=" + users +
                ", createdBy=" + createdBy +
                ", course=" + course +
                ", isClosed=" + isClosed +
                ", createdAt=" + createdAt +
                '}';
    }
}