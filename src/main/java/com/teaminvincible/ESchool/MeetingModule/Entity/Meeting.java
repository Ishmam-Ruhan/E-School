package com.teaminvincible.ESchool.MeetingModule.Entity;

import com.fasterxml.jackson.annotation.JsonFilter;
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
    @Column(unique = true,length = 36,columnDefinition = "VARCHAR(255)", nullable = false, updatable = false)
    private String meetingId;

    private String meetingTitle;

    private String meetingAgenda;
    private String meetingDescription;
    private String meetingAvailableLink;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date endTime;

    @ManyToMany(mappedBy = "meetings")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings"})
    private Set<UserDescription>  participants = new HashSet<>();

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "courseOwnerId")
    @JsonIgnoreProperties(value = {"courses","user","tasks","meetings","role"})
    private UserDescription createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties(value = {"courseOwner","students","tasks","meetings","courseJoiningCode","courseSubTitle","createdDate"})
    private Course course;

    private Boolean isClosed = false;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false,updatable = false)
    private Date createdAt;

    public Meeting() {
    }

    public Meeting(String meetingId, String meetingTitle, String meetingAgenda, String meetingDescription, String meetingAvailableLink, Date startTime, Date endTime, Set<UserDescription> users, UserDescription createdBy, Course course, Boolean isClosed, Date createdAt) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.meetingAgenda = meetingAgenda;
        this.meetingDescription = meetingDescription;
        this.meetingAvailableLink = meetingAvailableLink;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = users;
        this.createdBy = createdBy;
        this.course = course;
        this.isClosed = isClosed;
        this.createdAt = createdAt;
    }

    @PreRemove
    public void beforeRemovingEntity(){
        this.participants.forEach(userDescription -> {
            userDescription.removeMeetingFromUser(this);
        });
        this.createdBy  = null;
        this.course = null;
    }

    public void removeUserFromMeeting(UserDescription userDescription){
        this.participants.remove(userDescription);
        userDescription.getMeetings().remove(this);
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

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

    public String getMeetingAvailableLink() {
        return meetingAvailableLink;
    }

    public void setMeetingAvailableLink(String meetingAvailableLink) {
        this.meetingAvailableLink = meetingAvailableLink;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Set<UserDescription> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UserDescription> users) {
        this.participants = users;
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
                ", meetingDescription='" + meetingDescription + '\'' +
                ", meetingAvailableLink='" + meetingAvailableLink + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", participants=" + participants +
                ", createdBy=" + createdBy +
                ", course=" + course +
                ", isClosed=" + isClosed +
                ", createdAt=" + createdAt +
                '}';
    }
}