package com.teaminvincible.ESchool.TaskModule.Repository;

import com.teaminvincible.ESchool.TaskModule.Entity.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    public static Specification<Task> searchByTaskUserId(String userId){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("createdBy").get("userId"),userId);
        });
    }

    public static Specification<Task> searchByTaskCourseId(String courseId){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("course").get("courseId"),courseId);
        });
    }
}
