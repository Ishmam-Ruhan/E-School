package com.teaminvincible.ESchool.MeetingModule.ServiceImplementation;

import com.teaminvincible.ESchool.Configurations.Master.CurrentUser;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.DTO.CreateMeetingRequest;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingSearchCriteria;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingSpecification;
import com.teaminvincible.ESchool.MeetingModule.DTO.UpdateMeetingRequest;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.MeetingModule.Repository.MeetingRepository;
import com.teaminvincible.ESchool.MeetingModule.Service.MeetingService;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Service.UserDescriptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MeetingServiceImplementation implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;


    @Autowired
    private UserDescriptionService userDescriptionService;


    @Autowired
    private CurrentUser currentUser;

    public Meeting findMeetingById(String id){

        Optional<Meeting> meeting = meetingRepository.findById(id);

        if(meeting.isPresent()){
            return meeting.get();
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Meeting with id: "+id+" not found!");
    }



    @Override
    public Meeting createMeeting(String courseId, CreateMeetingRequest createMeetingRequest) throws CustomException {

        UserDescription userDescription = userDescriptionService.getUserDescription(currentUser.getCurrentUserId());

        if(userDescription.getRole() == Role.STUDENT)
            throw new CustomException(HttpStatus.BAD_REQUEST,"Students are not allowed to create a meeting! They can only participate.");

        Optional<Course> targetCourse = userDescriptionService.getCoursesOfAUser().stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .findFirst();

        if(targetCourse.isEmpty())
            throw new CustomException(HttpStatus.BAD_REQUEST,"Course unavailable! Can't process the request. Please try again.");

        Meeting meeting = new Meeting();
            meeting.setMeetingTitle(createMeetingRequest.getMeetingTitle());
            meeting.setMeetingAgenda(createMeetingRequest.getMeetingAgenda());
            meeting.setMeetingDescription(createMeetingRequest.getMeetingDescription());
            meeting.setMeetingAvailableLink(createMeetingRequest.getMeetingAvailableLink());
            meeting.setStartTime(createMeetingRequest.getStartTime());
            meeting.setEndTime(createMeetingRequest.getEndTime());

            meeting.setCourse(targetCourse.get());
            meeting.setCreatedBy(userDescription);

        try{
            meeting = meetingRepository.save(meeting);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process the request right now! Please try again.");
        }

        userDescriptionService.saveMeetingToUsers(targetCourse.get().getStudents(), meeting);

        return meeting;
    }

    @Override
    public Meeting updateMeeting(UpdateMeetingRequest updateMeetingRequest) throws CustomException {

        Meeting existingMeeting = findMeetingById(updateMeetingRequest.getMeetingId());

        if(!existingMeeting.getCreatedBy().getUserId().equals(currentUser.getCurrentUserId()))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Sorry! You're not the creator of this meeting!");

        existingMeeting.setMeetingTitle(updateMeetingRequest.getMeetingTitle());
        existingMeeting.setMeetingAgenda(updateMeetingRequest.getMeetingAgenda());
        existingMeeting.setMeetingDescription(updateMeetingRequest.getMeetingDescription());
        existingMeeting.setMeetingAvailableLink(updateMeetingRequest.getMeetingAvailableLink());
        existingMeeting.setStartTime(updateMeetingRequest.getStartTime());
        existingMeeting.setEndTime(updateMeetingRequest.getEndTime());

        try {
            meetingRepository.save(existingMeeting);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process it right now. Please try again.");
        }

        return existingMeeting;
    }

    @Override
    public String deleteMeeting(String meetingId) throws CustomException {

        Meeting meetingToRemove = findMeetingById(meetingId);

        UserDescription currentUserDescription = userDescriptionService.getUserDescription(currentUser.getCurrentUserId());

        if(!meetingToRemove.getCreatedBy().getUserId().equals(currentUserDescription.getUserId()))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Sorry! You're not the creator of the meeting.");

        try{
            meetingRepository.deleteById(meetingId);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't perform this operation right now!");
        }

        return "Successfully deleted meeting: "+meetingToRemove.getMeetingTitle();
    }

    @Override
    public String deleteCollectionOfMeeting(List<String> meetingIds) throws CustomException {
        for(String id : meetingIds){
            deleteMeeting(id);
        }
        return "Successfully deleted all meetings!";
    }


    @Override
    public Meeting getMeetingDetails(String meetingId) throws CustomException {
        return findMeetingById(meetingId);
    }

    @Override
    public Set<Meeting> getAllMeetingsOfACreator(String userId) throws CustomException {

        Set<Meeting> resultSet = new HashSet<>();
        resultSet.addAll(meetingRepository.findAll(MeetingSpecification.searchByMeetingUserId(userId)));

        return resultSet;
    }

    @Override
    public Set<Meeting> getAllMeetingOfACourse(String courseId) throws CustomException {
        Set<Meeting> resultSet = new HashSet<>();
        resultSet.addAll(meetingRepository.findAll(MeetingSpecification.searchByMeetingCourseId(courseId)));

        return resultSet;
    }

//    @Override
//    public Set<Meeting> getAllMeetingsFromCourse(String courseId) throws CustomException {
//        return (Set<Meeting>) meetingRepository.findAll(MeetingSpecification.searchByMeetingCourseId(courseId));
//    }

}
