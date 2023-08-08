package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);

    User findByUserNameAndIsDeleted(String userName, Boolean deleted);
    @Transactional
    void deleteByUserName(String userName);
    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description,Boolean deleted);


}
