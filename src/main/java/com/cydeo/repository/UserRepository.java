package com.cydeo.repository;

import com.cydeo.entity.User;
import com.cydeo.service.RoleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User findAllByUserName(String userName);
}
