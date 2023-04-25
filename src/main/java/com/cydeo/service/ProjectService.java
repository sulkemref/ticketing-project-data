package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> listAllProjects();
    ProjectDTO getByProjectCode(String code);
    void save(ProjectDTO dto);
    void update(ProjectDTO dto);
    void delete(String code);
    void safeDeleteByProjectCode(String code);
    void complete(String code);
    List<ProjectDTO> listAllProjectDetails();
}
