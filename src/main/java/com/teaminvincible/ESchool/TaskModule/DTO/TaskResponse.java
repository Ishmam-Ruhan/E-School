package com.teaminvincible.ESchool.TaskModule.DTO;

import java.util.Date;

public class TaskResponse {
    private String taskId;
    private String taskTitle;
    private Date dueDate;

    public TaskResponse() {
    }

    public TaskResponse(String taskId, String taskTitle, Date dueDate) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.dueDate = dueDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
