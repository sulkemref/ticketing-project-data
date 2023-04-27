package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,@Lazy ProjectService projectService,@Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        return userRepository.findAll(Sort.by("firstName"))
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDto(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO user) {
        userRepository.save(userMapper.convertToEntity(user));
    }


    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public UserDTO update(UserDTO user) {

        Long id  = userRepository.findByUserName(user.getUserName()).getId();

        User entity = userMapper.convertToEntity(user);

        entity.setId(id);

        userRepository.save(entity);

        return findByUserName(user.getUserName());
    }

    @Override
    public void safeDeleteByUserName(String userName) {

        User user = userRepository.findByUserName(userName);

        if(checkIfUserCanBeDeleted(user)){

            user.setIsDeleted(true);

            userRepository.save(user);
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {

        List<User> users = userRepository.findByRoleDescriptionIgnoreCase(role);

        return users.stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted(User user){

        switch (user.getRole().getDescription()){
            case "Manager" :
                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(
                  userMapper.convertToDto(user)
                );
                return projectDTOList.size()==0;
            case "Employee" :
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(
                        userMapper.convertToDto(user)
                );
                return taskDTOList.size()==0;

            case "Admin" : return false;

            default: return true;

        }

    }
}
