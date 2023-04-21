package com.cydeo.service;

import com.cydeo.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    public List<RoleDTO> listAllRoles();
    RoleDTO findById(Long id);

}
