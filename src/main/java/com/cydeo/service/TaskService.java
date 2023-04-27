package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {

    void save(TaskDTO task);

    List<TaskDTO> listAllTasks();

    void update (TaskDTO task);

    void delete(Long id);

    int totalNonCompletedTask(String projectCode);

    int totalCompletedTask(String projectCode);

    TaskDTO findById(Long id);

    void deleteByProject(ProjectDTO projectDTO);

    void completeAllByProject(ProjectDTO projectDTO);

    List<TaskDTO> listAllTasksByStatus(Status status);

    List<TaskDTO> listAllTasksByStatusIsNot(Status status);

}
