package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT count(t) FROM Task t WHERE t.project.projectCode=?1 " +
            "AND t.taskStatus<>'COMPLETE' AND t.isDeleted=false")
    int totalNonCompletedTasks(@Param("projectCode") String projectCode);


    @Query(value = "SELECT count(*) FROM tasks t " +
            "JOIN projects p " +
            "ON t.project_id=p.id " +
            "WHERE p.project_code=?1 " +
            "AND t.task_status='COMPLETE' " +
            "AND t.is_deleted IS false",nativeQuery = true)
    int totalCompleteTasks(@Param("projectCode") String projectCode);

    List<Task> findAllByProject(Project project);

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User user);

    List<Task> findAllByTaskStatusAndAssignedEmployee(Status status, User user);
}
