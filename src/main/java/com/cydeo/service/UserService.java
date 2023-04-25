package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO user);
    void deleteByUserName(String username);
    UserDTO update(UserDTO user);
    void safeDeleteByUserName(String userName);
    List<UserDTO> listAllByRole(String role);
}
