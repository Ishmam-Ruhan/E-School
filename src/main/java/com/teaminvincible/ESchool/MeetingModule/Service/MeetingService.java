package com.teaminvincible.ESchool.MeetingModule.Service;

import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.DTO.CreateMeetingRequest;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingResponse;
import com.teaminvincible.ESchool.MeetingModule.DTO.UpdateMeetingRequest;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface MeetingService {

    Meeting createMeeting(String courseId, CreateMeetingRequest createMeetingRequest) throws CustomException;

    MeetingResponse updateMeeting(UpdateMeetingRequest updateMeetingRequest) throws CustomException;

    String deleteMeeting(String meetingId) throws CustomException;

    Meeting getMeetingDetails(String meetingId) throws CustomException;

    Set<MeetingResponse> getAllMeetingsOfACreator(String userId) throws CustomException;

    Set<MeetingResponse> getAllMeetingOfACourse(String courseId) throws CustomException;
}
