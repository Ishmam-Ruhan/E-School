package com.teaminvincible.ESchool.CourseModule.ServiceImplementation;

import com.teaminvincible.ESchool.Configurations.Master.CurrentUser;
import com.teaminvincible.ESchool.CourseModule.DTO.*;
import com.teaminvincible.ESchool.CourseModule.Repository.CourseSpecification;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.CourseModule.Repository.CourseRepository;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingResponse;
import com.teaminvincible.ESchool.MeetingModule.Service.MeetingService;
import com.teaminvincible.ESchool.TaskModule.DTO.TaskResponse;
import com.teaminvincible.ESchool.TaskModule.Service.TaskService;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Service.UserDescriptionService;
import com.teaminvincible.ESchool.Utility.CodeGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private TaskService taskService;

    @Autowired
    private CurrentUser currentUser;

    public Course findCourseById(String courseId) throws CustomException{

        Optional<Course> course = courseRepository.findById(courseId);

        if(course.isPresent())return course.get();
        throw new CustomException(HttpStatus.NOT_FOUND,"No course found with id: "+courseId);
    }

    @Override
    public CourseResponse createCourse(CreateCourseRequest courseRequest) throws CustomException {

        UserDescription userDescription = userDescriptionService.getUserDescription();

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

        CourseResponse courseResponse
                = new CourseResponse();
        BeanUtils.copyProperties(course,courseResponse);

        return courseResponse;
    }

    @Override
    public CourseResponse joinCourse(String joiningCode) throws CustomException {
        UserDescription userDescription = userDescriptionService.getUserDescription();

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

        CourseResponse courseResponseForStudent
                = new CourseResponse();
        BeanUtils.copyProperties(course,courseResponseForStudent);

        return courseResponseForStudent;
    }

    @Override
    public String unEnrollFromACourse(String courseId) throws CustomException {

        Course courseToRemove= findCourseById(courseId);

        UserDescription userDescription = userDescriptionService.getUserDescription();

        if(!userDescription.checkIfUserAlreadyEnrolledThisCourse(courseToRemove))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Opps! You're not enrolled in this course.");

        userDescription.removeCourse(courseToRemove);

        userDescriptionService.updateUserDescription(userDescription);

        return "Successfully un-enrolled from course: "+courseToRemove.getCourseTitle();
    }

    @Override
    public CourseResponse updateCourse(UpdateCourseRequest updateCourseRequest) throws CustomException {
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

        CourseResponse courseResponse
                = new CourseResponse();
        BeanUtils.copyProperties(originalCourse,courseResponse);

        return courseResponse;
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
    public Set<CourseResponse> getAllCourseOfATeacher(String userId) throws CustomException {

     return courseRepository.findAll(CourseSpecification.findCourseByCourseOwner(userId))
             .stream()
             .map(course -> {
                 CourseResponse courseResponseForTeacher
                         = new CourseResponse();
                 BeanUtils.copyProperties(course, courseResponseForTeacher);
                 return courseResponseForTeacher;
             })
             .collect(Collectors.toSet());

    }

    @Override
    public Set<MeetingResponse> getAllMeetingsOfCourse(String courseId) throws CustomException {
        Course course = findCourseById(courseId);
        UserDescription userDescription = userDescriptionService.getUserDescription();

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
    public Set<TaskResponse> getAllTasksOfACourse(String courseId) throws CustomException {
        Course course = findCourseById(courseId);
        UserDescription userDescription = userDescriptionService.getUserDescription();

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

        return taskService.getTasksOfACourse(courseId);
    }

    @Override
    public Course getCourseDetails(String courseId) {
        Course course = findCourseById(courseId);
        UserDescription userDescription = userDescriptionService.getUserDescription();

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

        return course;
    }

    @Override
    public Set<Course> findCourse(CourseSearchCriteria searchCriteria) throws CustomException {
        return null;
    }

}
