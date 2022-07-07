package com.teaminvincible.ESchool.TaskModule.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class CreateTaskRequest {

    @NotEmpty(message = "Task title can't be empty!")
    private String taskTitle;

    @NotEmpty(message = "Task Details can't be empty!")
    private String taskDetails;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date dueDate;

    public CreateTaskRequest() {
    }

    public CreateTaskRequest(String taskTitle, String taskDetails, Date dueDate) {
        this.taskTitle = taskTitle;
        this.taskDetails = taskDetails;
        this.dueDate = dueDate;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
