package com.teaminvincible.ESchool.MeetingModule.DTO;

import java.util.Date;

public class MeetingResponse {
    private String meetingId;
    private String meetingTitle;
    private Date startTime;
    private Date endTime;
    private Boolean isClosed = false;

    public MeetingResponse() {
    }

    public MeetingResponse(String meetingId, String meetingTitle, Date startTime, Date endTime, Boolean isClosed) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isClosed = isClosed;
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

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }
}
