package com.teaminvincible.ESchool.MeetingModule.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class MeetingSearchCriteria {
    private String meetingId;
    private String meetingTitle;
    private String meetingAgenda;
    private Boolean isClosed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date createdAt;

    public MeetingSearchCriteria() {
    }

    public MeetingSearchCriteria(String meetingId, String meetingTitle, String meetingAgenda, Boolean isClosed, Date createdAt) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.meetingAgenda = meetingAgenda;
        this.isClosed = isClosed;
        this.createdAt = createdAt;
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
}
