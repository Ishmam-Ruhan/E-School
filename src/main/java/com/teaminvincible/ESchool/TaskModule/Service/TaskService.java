package com.teaminvincible.ESchool.TaskModule.Service;

import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.TaskModule.DTO.CreateTaskRequest;
import com.teaminvincible.ESchool.TaskModule.DTO.TaskResponse;
import com.teaminvincible.ESchool.TaskModule.DTO.UpdateTaskRequest;
import com.teaminvincible.ESchool.TaskModule.Entity.Task;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface TaskService {

    Task createTask(String courseId, CreateTaskRequest createTaskRequest) throws CustomException;

    Task updateTask(UpdateTaskRequest updateTaskRequest) throws CustomException;

    String deleteTask(String taskId) throws CustomException;

    Task getTaskDetails(String taskId) throws CustomException;

    Set<TaskResponse> getTasksOfACourse(String courseId) throws CustomException;

    Set<TaskResponse> getTasksOfACourseOwner(String userId) throws  CustomException;

}
