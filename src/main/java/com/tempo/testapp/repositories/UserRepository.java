package com.tempo.testapp.repositories;

import com.tempo.testapp.exceptions.UserNotFoundException;
import com.tempo.testapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserRepository extends JpaRepository<User, Long> {

    /*Integer createUser(User user) throws Exception;
    User findById(Integer id);
    User findByUserName(String username) throws UsernameNotFoundException;
    boolean findByUsername(String user_name) throws UserNotFoundException;
    boolean findByUserEmail(String email);
    User findByEmailAndPassword(String email, String password);

     */
}
