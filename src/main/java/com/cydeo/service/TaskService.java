package com.cydeo.service;

import com.cydeo.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    void save(TaskDTO task);

    List<TaskDTO> listAllTasks();

    void update (TaskDTO task);

    void delete(Long id);

    int totalNonCompletedTask(String projectCode);

    int totalCompletedTask(String projectCode);

    TaskDTO findById(Long id);



}
