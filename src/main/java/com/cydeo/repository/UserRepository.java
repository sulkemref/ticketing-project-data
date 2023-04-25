package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User findByUserName(String userName);
    @Transactional
    void deleteByUserName(String userName);
    List<User> findByRoleDescriptionIgnoreCase(String description);


}
