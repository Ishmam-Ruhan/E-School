package com.teaminvincible.ESchool.MeetingModule.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class UpdateMeetingRequest {

    @Size(min = 1,message = "Field must not be empty!")
    private String meetingId;
    @Size(min = 1,message = "Field must not be empty!")
    private String meetingTitle;

    @Size(min = 1,message = "Field must not be empty!")
    private String meetingAgenda;

    private String meetingDescription;

    private String meetingAvailableLink;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date endTime;

    public UpdateMeetingRequest() {
    }

    public UpdateMeetingRequest(String meetingId, String meetingTitle, String meetingAgenda, String meetingDescription, String meetingAvailableLink, Date startTime, Date endTime) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.meetingAgenda = meetingAgenda;
        this.meetingDescription = meetingDescription;
        this.meetingAvailableLink = meetingAvailableLink;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
