package com.tempo.testapp.service;

import com.tempo.testapp.exceptions.UserNotFoundException;
import com.tempo.testapp.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User registerUser(User user) throws Exception;
    boolean checkIfUsernameExists(String username) throws UserNotFoundException;
    boolean checkIfEmailExists(String email) throws  UserNotFoundException;
    ResponseEntity validateUser(String username, String email, String password);
}
