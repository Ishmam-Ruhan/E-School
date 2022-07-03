package com.teaminvincible.ESchool.MeetingModule.ServiceImplementation;

import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingSearchCriteria;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingSpecification;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.MeetingModule.Repository.MeetingRepository;
import com.teaminvincible.ESchool.MeetingModule.Service.MeetingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MeetingServiceImplementation implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;



    public Meeting findMeetingById(String id){

        Optional<Meeting> meeting = meetingRepository.findById(id);

        if(meeting.isPresent()){
            return meeting.get();
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Meeting with id: "+id+" not found!");
    }



    @Override
    public Meeting createMeeting(Meeting meeting) throws CustomException {

        if(meeting.getCourse() == null || meeting.getCreatedBy() == null)
            throw new CustomException(HttpStatus.BAD_REQUEST,"Must have to include a valid user and a course!");

        Meeting meeting1 = null;

        try{
            meeting1 = meetingRepository.save(meeting);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process the request right now! Please try again.");
        }

        return meeting1;
    }

    @Override
    public Meeting updateMeeting(Meeting meeting) throws CustomException {

        if(meeting.getCourse() == null || meeting.getCreatedBy() == null)
            throw new CustomException(HttpStatus.BAD_REQUEST,"Must have to include a valid user and a course!");

        Meeting previousMeeting = findMeetingById(meeting.getMeetingId());

        Meeting updatedMeeting = new Meeting();

        BeanUtils.copyProperties(meeting,updatedMeeting);

        updatedMeeting.setMeetingId(previousMeeting.getMeetingId());

        return createMeeting(updatedMeeting);
    }

    @Override
    public String deleteMeeting(String meetingId) throws CustomException {

        Meeting meeting = findMeetingById(meetingId);

        try{
            meetingRepository.deleteById(meeting.getMeetingId());
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }

        return "Successfully deleted meeting: "+meeting.getMeetingTitle();
    }

    @Override
    public String deleteCollectionOfMeeting(List<String> meetingIds) throws CustomException {
        for(String id : meetingIds){
            deleteMeeting(id);
        }
        return "Successfully deleted all meetings!";
    }

    @Override
    public Set<Meeting> getAllMeetingsFromUser(String userId) throws CustomException {
        return (Set<Meeting>) meetingRepository.findAll(MeetingSpecification.searchByMeetingUserId(userId));
    }

    @Override
    public Meeting getMeetingDetails(String meetingId) throws CustomException {
        return findMeetingById(meetingId);
    }

    @Override
    public Set<Meeting> getAllMeetingsFromCourse(String courseId) throws CustomException {
        return (Set<Meeting>) meetingRepository.findAll(MeetingSpecification.searchByMeetingCourseId(courseId));
    }

    @Override
    public Set<Meeting> searchMeetingsFromUser(String userId, MeetingSearchCriteria meetingSearchCriteria) throws CustomException {
        return null;
    }

    @Override
    public Set<Meeting> searchMeetingsFromCourse(String courseId, MeetingSearchCriteria meetingSearchCriteria) throws CustomException {
        return null;
    }

//    public Set<Meeting> searchMeeting(Boolean isUser, String id, MeetingSearchCriteria searchCriteria){
//
//        List<Specification<Meeting>> specifications = new ArrayList<>();
//
//        if(isUser) specifications.add(MeetingSpecification.searchByMeetingUserId(id));
//        else specifications.add(MeetingSpecification.searchByMeetingCourseId(id));
//
//        if(Objects.nonNull(searchCriteria.getMeetingId())){
//            specifications.add(MeetingSpecification.searchByMeetingId(searchCriteria.getMeetingId()));
//        }
//
//        if(Objects.nonNull(searchCriteria.getMeetingTitle())){
//            specifications.add(MeetingSpecification.searchByMeetingTitle(searchCriteria.getMeetingTitle()));
//        }
//
//        if(Objects.nonNull(searchCriteria.getMeetingAgenda())){
//            specifications.add(MeetingSpecification.searchByMeetingAgenda(searchCriteria.getMeetingAgenda()));
//        }
//
//        if(Objects.nonNull(searchCriteria.getClosed())){
//            specifications.add(MeetingSpecification.searchByMeetingIsClosed(searchCriteria.getClosed()));
//        }
//
//        if(Objects.nonNull(searchCriteria.getCreatedAt())){
//            specifications.add(MeetingSpecification.searchByMeetingCreatedAt(searchCriteria.getCreatedAt()));
//        }
//
//        meetingRepository.findAll();
//
//    }
}
