package com.teaminvincible.ESchool.TaskModule.ServiceImplementation;

import com.teaminvincible.ESchool.Configurations.Master.CurrentUser;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.TaskModule.DTO.CreateTaskRequest;
import com.teaminvincible.ESchool.TaskModule.DTO.TaskResponse;
import com.teaminvincible.ESchool.TaskModule.DTO.UpdateTaskRequest;
import com.teaminvincible.ESchool.TaskModule.Entity.Task;
import com.teaminvincible.ESchool.TaskModule.Repository.TaskRepository;
import com.teaminvincible.ESchool.TaskModule.Repository.TaskSpecification;
import com.teaminvincible.ESchool.TaskModule.Service.TaskService;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Service.UserDescriptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class TaskServiceImplementations implements TaskService {

    @Autowired
    private UserDescriptionService userDescriptionService;

    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private TaskRepository taskRepository;

    private Task findTaskById(String taskId){
        Optional<Task> task = taskRepository.findById(taskId);

        if(task.isPresent())return task.get();

        throw new CustomException(HttpStatus.BAD_REQUEST,"No task found with Id: "+taskId);
    }

    @Override
    public Task createTask(String courseId, CreateTaskRequest createTaskRequest) throws CustomException {

        UserDescription userDescription = userDescriptionService.getUserDescription(currentUser.getCurrentUserId());

        if(userDescription.getRole() == Role.STUDENT)
            throw new CustomException(HttpStatus.BAD_REQUEST,"Students are not allowed to create task!");

        Optional<Course> targetCourse = userDescriptionService.getCoursesOfAUser().stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .findFirst();

        if(targetCourse.isEmpty())
            throw new CustomException(HttpStatus.BAD_REQUEST,"Course unavailable! Can't process the request. Please try again.");


        Task newTask = new Task();
            newTask.setTaskTitle(createTaskRequest.getTaskTitle());
            newTask.setTaskDetails(createTaskRequest.getTaskDetails());
            newTask.setDueDate(createTaskRequest.getDueDate());

            newTask.setCourse(targetCourse.get());
            newTask.setCreatedBy(userDescription);

        try{
            taskRepository.save(newTask);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process your request right now! Please try again.");
        }

        userDescriptionService.saveTasksToUsers(targetCourse.get().getStudents(), newTask);

        return newTask;
    }

    @Override
    public Task updateTask(UpdateTaskRequest updateTaskRequest) throws CustomException {

        Task existingTask = findTaskById(updateTaskRequest.getTaskId());

        if(!existingTask.getCreatedBy().getUserId().equals(currentUser.getCurrentUserId()))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Sorry! You're not the creator of this task!");

        existingTask.setTaskTitle(updateTaskRequest.getTaskTitle());
        existingTask.setTaskDetails(updateTaskRequest.getTaskDetails());
        existingTask.setDueDate(updateTaskRequest.getDueDate());

        try{
            taskRepository.save(existingTask);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process the request right now. Please try again.");
        }

        return existingTask;
    }

    @Override
    public String deleteTask(String taskId) throws CustomException {
        Task taskToRemove = findTaskById(taskId);

        UserDescription currentUserDescription = userDescriptionService.getUserDescription(currentUser.getCurrentUserId());

        if(!taskToRemove.getCreatedBy().getUserId().equals(currentUserDescription.getUserId()))
            throw new CustomException(HttpStatus.BAD_REQUEST,"Sorry! You're not the creator of the task.");

        try{
            taskRepository.deleteById(taskId);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't perform this operation right now!");
        }

        return "Successfully deleted task";
    }

    @Override
    public Task getTaskDetails(String taskId) throws CustomException {
        return findTaskById(taskId);
    }

    @Override
    public Set<TaskResponse> getTasksOfACourse(String courseId) throws CustomException {

        return taskRepository
                .findAll(TaskSpecification.searchByTaskCourseId(courseId))
                .stream()
                .map(task -> {
                    TaskResponse taskResponse = new TaskResponse();
                    BeanUtils.copyProperties(task,taskResponse);
                    return taskResponse;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TaskResponse> getTasksOfACourseOwner(String userId) throws CustomException {

        return taskRepository
                .findAll(TaskSpecification.searchByTaskUserId(userId))
                .stream()
                .map(task -> {
                    TaskResponse taskResponse = new TaskResponse();
                    BeanUtils.copyProperties(task,taskResponse);
                    return taskResponse;
                })
                .collect(Collectors.toSet());
    }
}
