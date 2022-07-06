package com.teaminvincible.ESchool.CourseModule.ServiceImplementation;

import com.teaminvincible.ESchool.Configurations.Master.CurrentUser;
import com.teaminvincible.ESchool.CourseModule.DTO.CourseSearchCriteria;
import com.teaminvincible.ESchool.CourseModule.DTO.CourseSpecification;
import com.teaminvincible.ESchool.CourseModule.DTO.CreateCourseRequest;
import com.teaminvincible.ESchool.CourseModule.DTO.UpdateCourseRequest;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.CourseModule.Repository.CourseRepository;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.MeetingModule.Service.MeetingService;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Service.UserDescriptionService;
import com.teaminvincible.ESchool.Utility.CodeGenerator;
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

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private CurrentUser currentUser;

    public Course findCourseById(String courseId) throws CustomException{

        Optional<Course> course = courseRepository.findById(courseId);

        if(course.isPresent())return course.get();
        throw new CustomException(HttpStatus.NOT_FOUND,"No course found with id: "+courseId);
    }

    @Override
    public Course createCourse(CreateCourseRequest courseRequest) throws CustomException {

        UserDescription userDescription = userDescriptionService.getUserDescription(currentUser.getCurrentUserId());

        if(userDescription.getRole() == Role.STUDENT)
            throw new CustomException(HttpStatus.BAD_REQUEST,"Students are not allowed to create a course! They can only join.");

        Course course = new Course();
        course.setCourseTitle(courseRequest.getCourseTitle());
        course.setCourseSubTitle(courseRequest.getCourseSubTitle());
        course.setCourseOwner(userDescription);

        if(course.getCourseJoiningCode() == null)
            course.setCourseJoiningCode(CodeGenerator.generateCourseJoinCode());


        try{
            courseRepository.save(course);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process the request right now! Please try again.");
        }

        return course;
    }

    @Override
    public Course joinCourse(String joiningCode) throws CustomException {
        UserDescription userDescription = userDescriptionService.getUserDescription(currentUser.getCurrentUserId());

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
    public String unEnrollFromACourse(String courseId) throws CustomException {

        Course courseToRemove= findCourseById(courseId);

        UserDescription userDescription = userDescriptionService.getUserDescription(currentUser.getCurrentUserId());

        if(!userDescription.checkIfUserAlreadyEnrolledThisCourse(courseToRemove))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Opps! You're not enrolled in this course.");

        userDescription.removeCourse(courseToRemove);

        userDescriptionService.updateUserDescription(userDescription);

        return "Successfully un-enrolled from course: "+courseToRemove.getCourseTitle();
    }

    @Override
    public Course updateCourse(UpdateCourseRequest updateCourseRequest) throws CustomException {
        Course originalCourse = findCourseById(updateCourseRequest.getCourseId());

        if(Objects.isNull(originalCourse))
            throw new CustomException(HttpStatus.BAD_REQUEST,"No course found with course-id: "+updateCourseRequest.getCourseId());

        if(!originalCourse.getCourseOwner().getUserId().equals(currentUser.getCurrentUserId()))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Sorry! You're not the owner of this course.");

        originalCourse.setCourseTitle(updateCourseRequest.getCourseTitle());
        originalCourse.setCourseSubTitle(updateCourseRequest.getCourseSubTitle());

        try {
            courseRepository.save(originalCourse);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Can't Process the request right now!");
        }
        return originalCourse;
    }

    @Override
    public String deleteCourse(String courseId) throws CustomException {

        Course course = findCourseById(courseId);

        if(!course.getCourseOwner().getUserId().equals(currentUser.getCurrentUserId()))
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
    public Set<Meeting> getAllMeetingsOfCourse(String courseId) throws CustomException {
        Course course = findCourseById(courseId);
        UserDescription userDescription = userDescriptionService.getUserDescription(currentUser.getCurrentUserId());

        if(userDescription.getRole().equals(Role.STUDENT)){
            Optional<UserDescription> enrolled = course.getStudents().stream()
                    .filter(student -> student.getUserId().equals(currentUser.getCurrentUserId()))
                    .findFirst();

            if(enrolled.isEmpty())
                throw new CustomException(HttpStatus.BAD_REQUEST,"Opps! You're not enrolled in this course!");
        }

        else if(!userDescription.getUserId().equals(course.getCourseOwner().getUserId())){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Opps! You're not the authority of this course!");
        }


        return meetingService.getAllMeetingOfACourse(courseId);
    }

    @Override
    public Set<Course> findCourse(CourseSearchCriteria searchCriteria) throws CustomException {
        return null;
    }
}
