package com.tempo.testapp.repositories;

import com.tempo.testapp.exceptions.UserNotFoundException;
import com.tempo.testapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
/*
    Integer createUser(User user) throws Exception;
    User findById(Integer id);
    User findByUserName(String username) throws UsernameNotFoundException;
    boolean findByUsername(String user_name) throws UserNotFoundException;
    boolean findByUserEmail(String email);
    User findByEmailAndPassword(String email, String password);
*/
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    //Boolean existByEmailAndPassword(String email, String password);
   Optional<User> findByEmailAndPassword(String email, String password);
    User findUsernameByEmail(String email);
}
