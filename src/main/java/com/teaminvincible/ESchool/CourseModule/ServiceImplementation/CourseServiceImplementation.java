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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Primary
public class CourseServiceImplementation implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserDescriptionService userDescriptionService;


    public Course findCourseById(String courseId) throws CustomException{

        Optional<Course> course = courseRepository.findById(courseId);

        if(course.isPresent())return course.get();
        throw new CustomException(HttpStatus.NOT_FOUND,"No course found with id: "+courseId);
    }

    @Override
    public Course createCourse(String userId, Course course) throws CustomException {

        UserDescription userDescription = userDescriptionService.getUserDescription(userId);

        if(userDescription.getRole() == Role.STUDENT)
            throw new CustomException(HttpStatus.BAD_REQUEST,"Students are not allowed to create a course! They can only join.");

        Course course1;

        if(course.getCourseJoiningCode() == null)
            course.setCourseJoiningCode(CodeGenerator.generateCourseJoinCode());

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

        if(userDescription.getRole() == Role.TEACHER)
            throw new CustomException(HttpStatus.BAD_REQUEST,"Teachers are not allowed to join a course! They can only create course.");

        Course course = courseRepository.findBycourseJoiningCode(joiningCode);

        if(course == null)
            throw new CustomException(HttpStatus.BAD_REQUEST,"No course found! Please provide a valid course Joining Code.");

        if(userDescription.checkIfUserAlreadyEnrolledThisCourse(course))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Opps! Already enrolled in this course.");

        Set<Course> courses = userDescription.getCourses();
        courses.add(course);

        userDescription.setCourses(courses);

        userDescriptionService.updateUserDescription(userDescription);

        return course;
    }

    @Override
    public String unEnrollFromACourse(String userId, String courseId) throws CustomException {

        Course courseToRemove= findCourseById(courseId);

        if(Objects.isNull(courseToRemove))
            throw new CustomException(HttpStatus.BAD_REQUEST,"No course found with course-id: "+courseId);

        UserDescription userDescription = userDescriptionService.getUserDescription(userId);

        if(!userDescription.checkIfUserAlreadyEnrolledThisCourse(courseToRemove))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Opps! You're not enrolled in this course.");

        userDescription.removeCourse(courseToRemove);

        userDescriptionService.updateUserDescription(userDescription);

        return "Successfully un-enrolled from course: "+courseToRemove.getCourseTitle();
    }

    @Override
    public Course updateCourse(String userId, Course course) throws CustomException {
        Course oldCourse = findCourseById(course.getCourseId());

        if(Objects.isNull(oldCourse))
            throw new CustomException(HttpStatus.BAD_REQUEST,"No course found with course-id: "+course.getCourseId());

        if(!oldCourse.getCourseOwner().getUserId().equals(userId))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Sorry! You're not the owner of this course.");

        Course updatedCourse = new Course();

        BeanUtils.copyProperties(course,updatedCourse);

        updatedCourse.setCourseId(oldCourse.getCourseId());

        return createCourse(userId,updatedCourse);
    }

    @Override
    public String deleteCourse(String userId, String courseId) throws CustomException {

        Course course = findCourseById(courseId);

        if(Objects.isNull(course))
            throw new CustomException(HttpStatus.BAD_REQUEST,"No course found with course-id: "+course.getCourseId());

        if(!course.getCourseOwner().getUserId().equals(userId))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Sorry! You're not the owner of this course.");

        try{
            courseRepository.deleteById(courseId);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process the request right now. Please try again.");
        }

        return "Course Deleted Successfully!";
    }

    @Override
    public Set<Course> getAllCourseOfATeacher(String userId) throws CustomException {

        Set<Course> courseSet = new HashSet<>();

        courseSet.addAll( courseRepository.findAll(CourseSpecification.findCourseByCourseOwner(userId)));

        return courseSet;
    }

    @Override
    public Set<Course> findCourse(String userId, CourseSearchCriteria searchCriteria) throws CustomException {
        return null;
    }
}
