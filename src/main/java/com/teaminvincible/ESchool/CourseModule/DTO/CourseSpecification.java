package com.teaminvincible.ESchool.CourseModule.DTO;

import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import org.springframework.data.jpa.domain.Specification;

public final class CourseSpecification {

    public static Specification<Course> findCourseByCourseOwner(String userId){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("courseOwner").get("userId"), userId);
        };
    }

    public static Specification<Course> findCourseByCourseId(String courseId){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("courseId"), courseId);
        };
    }

    public static Specification<Course> findCourseByEnrolledStudentId(String userId){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("students").get("userId"), userId);
        };
    }

}
