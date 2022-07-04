package com.teaminvincible.ESchool.CourseModule.ServiceImplementation;

import com.teaminvincible.ESchool.CourseModule.DTO.CourseSearchCriteria;
import com.teaminvincible.ESchool.CourseModule.DTO.CourseSpecification;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.CourseModule.Repository.CourseRepository;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Service.UserDescriptionService;
import com.teaminvincible.ESchool.Utility.CodeGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Primary
public class CourseServiceImplementation implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserDescriptionService userDescriptionService;


    public Course findCourseById(String userID, String courseId) throws CustomException{

        Specification<Course> spec = Specification.where(
                CourseSpecification.findCourseByCourseOwner(userID)
                        .and(CourseSpecification.findCourseByCourseId(courseId))
        );

        Course course = courseRepository.findAll(spec).get(0);

        if(course != null)return course;
        throw new CustomException(HttpStatus.NOT_FOUND,"No course found with id: "+courseId);
    }

    public Course findCourseById(String courseId) throws CustomException{

        Optional<Course> course = courseRepository.findById(courseId);

        if(course.isPresent())return course.get();
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

        UserDescription userDescription = userDescriptionService.getUserDescription(userId);

        if(userDescription.getRole() == Role.STUDENT)
            throw new CustomException(HttpStatus.BAD_REQUEST,"Students are not allowed to create a course! They can only join.");

        course.setCourseOwner(userDescription);

        try{
            course1 = courseRepository.save(course);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process the request right now! Please try again.");
        }

        return course1;
    }

    @Override
    public Course joinCourse(String userId, String joiningCode) throws CustomException {
        UserDescription userDescription = userDescriptionService.getUserDescription(userId);

        System.out.println("User Role: "+userDescription.getRole());

        if(userDescription.getRole() == Role.TEACHER)
            throw new CustomException(HttpStatus.BAD_REQUEST,"Teachers are not allowed to join a course! They can only create course.");

        Course course = courseRepository.findBycourseJoiningCode(joiningCode);

        System.out.println("User Role: "+userDescription.getRole());


        if(course == null)
            throw new CustomException(HttpStatus.BAD_REQUEST,"No course found! Please provide a valid course joining code.");

        // Check user exist in this course or not

        Set<Course> courses = userDescription.getCourses();
        courses.add(course);

        userDescription.setCourses(courses);

        System.out.println("User Role: "+userDescription.getRole());


        userDescriptionService.updateUserDescription(userDescription);

        return course;
    }

    @Override
    public String unEnrollFromACourse(String userId, String courseId) throws CustomException {

        Course courseToRemove= findCourseById(courseId);

        UserDescription userDescription = userDescriptionService.getUserDescription(userId);

        userDescription.removeCourse(courseToRemove);

        userDescriptionService.updateUserDescription(userDescription);


        return "Successfully un-enrolled from course: "+courseToRemove.getCourseTitle();
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

        try{
            courseRepository.deleteById(courseId);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process the request right now. Please try again.");
        }

        return "Course Deleted Successfully!";
    }

    @Override
    public Set<Course> getAllCourseOfAUser(String userId) throws CustomException {

        Set<Course> courseSet = new HashSet<>();
        courseSet.addAll( courseRepository.findAll(CourseSpecification.findCourseByCourseOwner(userId)));

        return courseSet;
    }

    @Override
    public Set<Course> findCourse(String userId, CourseSearchCriteria searchCriteria) throws CustomException {
        return null;
    }
}
