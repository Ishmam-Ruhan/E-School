package com.teaminvincible.ESchool.TaskModule.Controller;

import com.teaminvincible.ESchool.Annotations.DeleteAPI;
import com.teaminvincible.ESchool.Annotations.GetAPI;
import com.teaminvincible.ESchool.Annotations.PostAPI;
import com.teaminvincible.ESchool.Annotations.PutAPI;
import com.teaminvincible.ESchool.Output.Response;
import com.teaminvincible.ESchool.TaskModule.DTO.CreateTaskRequest;
import com.teaminvincible.ESchool.TaskModule.DTO.UpdateTaskRequest;
import com.teaminvincible.ESchool.TaskModule.Service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/task-management")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(
            summary = "Create new task"
    )
    @PostAPI("/create-task")
    @Schema(name = "Create Task Schema", implementation = CreateTaskRequest.class)
    public ResponseEntity createTask(@RequestParam String courseId, @Valid @RequestBody CreateTaskRequest createTaskRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(
                   HttpStatus.CREATED,
                   true,
                   "New task created successfully!",
                   taskService.createTask(courseId,createTaskRequest)
                ));
    }

    @Operation(
            summary = "Update task"
    )
    @PutAPI("/update-task")
    @Schema(name = "Update Task Schema", implementation = UpdateTaskRequest.class)
    public ResponseEntity updateTask(@Valid @RequestBody UpdateTaskRequest updateTaskRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Task updated successfully!",
                        taskService.updateTask(updateTaskRequest)
                ));
    }

    @Operation(
            summary = "Delete task"
    )
    @DeleteAPI("/delete-task")
    public ResponseEntity deleteTask(@RequestParam String taskId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Task deleted successfully!",
                        taskService.deleteTask(taskId)
                ));
    }

    @Operation(
            summary = "Get task details"
    )
    @GetAPI("/get-details")
    public ResponseEntity getTaskDetails(@RequestParam String taskId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        HttpStatus.OK,
                        true,
                        "Task fetched successfully!",
                        taskService.getTaskDetails(taskId)
                ));
    }
}
