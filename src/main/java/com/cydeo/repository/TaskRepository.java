package com.cydeo.repository;

import com.cydeo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT count(t) FROM Task t WHERE t.project.projectCode=?1 " +
            "AND t.taskStatus<>'COMPLETE'")
    int totalNonCompletedTasks(@Param("projectCode") String projectCode);


    @Query(value = "SELECT count(*) FROM tasks t " +
            "JOIN projects p " +
            "ON t.project_id=p.id " +
            "WHERE p.project_code=?1 " +
            "AND t.task_status='COMPLETE'",nativeQuery = true)
    int totalCompleteTasks(@Param("projectCode") String projectCode);
}
