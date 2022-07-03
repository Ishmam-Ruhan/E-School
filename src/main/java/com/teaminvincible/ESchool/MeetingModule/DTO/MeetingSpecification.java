package com.teaminvincible.ESchool.MeetingModule.DTO;

import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class MeetingSpecification {

    public static Specification<Meeting> searchByMeetingId(String meetingId){
        return ((root, query, criteriaBuilder) -> {
           return criteriaBuilder.equal(root.get("meetingId"),meetingId);
        });
    }

    public static Specification<Meeting> searchByMeetingUserId(String userId){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("createdBy").get("userId"),userId);
        });
    }

    public static Specification<Meeting> searchByMeetingCourseId(String courseId){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("course").get("courseId"),courseId);
        });
    }

    public static Specification<Meeting> searchByMeetingTitle(String meetingTitle){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("meetingTitle"),"%"+meetingTitle+"%");
        });
    }

    public static Specification<Meeting> searchByMeetingAgenda(String meetingAgenda){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("meetingAgenda"),"%"+meetingAgenda+"%");
        });
    }

    public static Specification<Meeting> searchByMeetingIsClosed(Boolean isClosed){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("isClosed"),isClosed);
        });
    }

    public static Specification<Meeting> searchByMeetingCreatedAt(Date createdAt){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("createdAt"),createdAt);
        });
    }
}
