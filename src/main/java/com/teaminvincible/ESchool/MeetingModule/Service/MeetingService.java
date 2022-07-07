package com.teaminvincible.ESchool.MeetingModule.Service;

import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.DTO.CreateMeetingRequest;
import com.teaminvincible.ESchool.MeetingModule.DTO.UpdateMeetingRequest;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface MeetingService {

    Meeting createMeeting(String courseId, CreateMeetingRequest createMeetingRequest) throws CustomException;

    Meeting updateMeeting(UpdateMeetingRequest updateMeetingRequest) throws CustomException;

    String deleteMeeting(String meetingId) throws CustomException;

    Meeting getMeetingDetails(String meetingId) throws CustomException;

    Set<Meeting> getAllMeetingsOfACreator(String userId) throws CustomException;

    Set<Meeting> getAllMeetingOfACourse(String courseId) throws CustomException;

    // Set<Meeting> getAllMeetingsFromCourse(String courseId) throws CustomException;  have to implement in course module
}
