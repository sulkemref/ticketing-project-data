package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

    public ProjectServiceImpl(UserRepository userRepository, ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        return projectRepository.findAll(Sort.by("projectCode"))
                .stream()
                .map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        return projectMapper.convertToDto(projectRepository.findByProjectCode(code));
    }

    @Override
    public void save(ProjectDTO dto) {

        if (dto.getProjectStatus()==null)
                    dto.setProjectStatus(Status.OPEN);

        projectRepository.save(projectMapper.convertToEntity(dto));
    }

    @Override
    public void update(ProjectDTO dto) {

        Project existing = projectRepository.findByProjectCode(dto.getProjectCode());
        Project updated = projectMapper.convertToEntity(dto);

        updated.setId(existing.getId());
        updated.setProjectStatus(existing.getProjectStatus());

        projectRepository.save(updated);
    }

    @Override
    public void delete(String code) {

    }

    @Override
    public void safeDeleteByProjectCode(String code) {

        Project project = projectRepository.findByProjectCode(code);

        project.setIsDeleted(true);

        projectRepository.save(project);
    }

    @Override
    public void complete(String code) {
        Project project = projectRepository.findByProjectCode(code);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        UserDTO currentDTO = userService.findByUserName("harold@manager.com");

        User user = userMapper.convertToEntity(currentDTO);

        List<Project> list= projectRepository.findAllByAssignedManager(user);

        return list.stream()
                .map(project -> {

                    ProjectDTO obj = projectMapper.convertToDto(project);


                    obj.setUnfinishedTaskCounts(
                            taskService.totalNonCompletedTask(project.getProjectCode())
                    );
                    obj.setCompleteTaskCounts(
                            taskService.totalCompletedTask(project.getProjectCode())
                    );

                    return obj;
                })
                .collect(Collectors.toList());
    }

}
