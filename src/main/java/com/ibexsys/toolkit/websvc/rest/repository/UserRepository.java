package com.ibexsys.toolkit.websvc.rest.repository;

import com.ibexsys.toolkit.websvc.rest.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{



    List<User> findByName(String name);

    List<User> findByNameAndId(String name, Long id);

    List<User> findByNameOrderById(String name);

    List<User> deleteByNameAndId(String name, Long id);

    //@ToDo - Add more customs for examples

}