package com.tempo.testapp.service;

import com.tempo.testapp.exceptions.UserNotFoundException;
import com.tempo.testapp.entity.User;

public interface UserService {
    User registerUser(User user) throws Exception;
    boolean checkIfUsernameExists(String username) throws UserNotFoundException;
    boolean checkIfEmailExists(String email) throws  UserNotFoundException;
    User validateUser(String email, String password);
}
