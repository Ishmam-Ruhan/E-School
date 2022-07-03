package com.teaminvincible.ESchool.MeetingModule.Service;

import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingSearchCriteria;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface MeetingService {

    Meeting createMeeting(Meeting meeting) throws CustomException;

    Meeting updateMeeting(Meeting meeting) throws CustomException;

    String deleteMeeting(String meetingId) throws CustomException;

    String deleteCollectionOfMeeting(List<String> meetingIds) throws CustomException;

    Set<Meeting> getAllMeetingsFromUser(String userId) throws CustomException;

    Meeting getMeetingDetails(String meetingId) throws CustomException;

    Set<Meeting> searchMeetingsFromUser(String userId, MeetingSearchCriteria meetingSearchCriteria) throws CustomException;

    Set<Meeting> getAllMeetingsFromCourse(String courseId) throws CustomException;

    Set<Meeting> searchMeetingsFromCourse(String courseId, MeetingSearchCriteria meetingSearchCriteria) throws CustomException;
}
