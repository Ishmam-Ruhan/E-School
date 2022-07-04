package com.teaminvincible.ESchool.CourseModule.ServiceImplementation;

import com.teaminvincible.ESchool.CourseModule.DTO.CourseSearchCriteria;
import com.teaminvincible.ESchool.CourseModule.DTO.CourseSpecification;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.CourseModule.Repository.CourseRepository;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Repository.UserDescriptionRepository;
import com.teaminvincible.ESchool.Utility.CodeGenerator;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Primary
public class CourseServiceImplementation implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserDescriptionRepository userDescriptionRepository;

    public Course findCourseById(String userID, String courseId) throws CustomException{

        Specification<Course> spec = Specification.where(
                CourseSpecification.findCourseByCourseOwner(userID)
                        .and(CourseSpecification.findCourseByCourseId(courseId))
        );

        Course course = courseRepository.findAll(spec).get(0);

        if(course != null)return course;
        throw new CustomException(HttpStatus.NOT_FOUND,"No course found with id: "+courseId);
    }


    @Override
    public Course createCourse(String userId, Course course) throws CustomException {
        /**
         *  Before saving we have to use "UserDescriptionService" to validate the user, exist, active or not
         */

        Course course1 = null;

        if(course.getCourseJoiningCode() == null)
            course.setCourseJoiningCode(CodeGenerator.generateCourseJoinCode());

        UserDescription userDescription = userDescriptionRepository.findByuserId(userId).orElse(null);

        course.setCourseOwner(userDescription);

        System.out.println("Course Controller: Create Course: "+course);

        try{
            course1 = courseRepository.save(course);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process the request right now! Please try again.");
        }

        return course1;
    }

    @Override
    public Course updateCourse(String userId, Course course) throws CustomException {
        Course oldCourse = findCourseById(userId, course.getCourseId());

        Course updatedCourse = new Course();

        BeanUtils.copyProperties(course,updatedCourse);

        updatedCourse.setCourseId(oldCourse.getCourseId());

        return createCourse(userId,updatedCourse);
    }

    @Override
    public String deleteCourse(String userId, String courseId) throws CustomException {
        Course course = findCourseById(userId, courseId);

        try{
            courseRepository.deleteById(courseId);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process the request right now. Please try again.");
        }

        return "Course Deleted Successfully!";
    }

    @Override
    public Set<Course> getAllCourseOfAUser(String userId) throws CustomException {

        Specification<Course> spec = Specification.where(CourseSpecification.findCourseByCourseOwner(userId)
                .or(CourseSpecification.findCourseByStudent(userId)));

        return (Set<Course>) courseRepository.findAll(spec);
    }

    @Override
    public Set<Course> findCourse(String userId, CourseSearchCriteria searchCriteria) throws CustomException {
        return null;
    }
}
