package com.tempo.testapp.service;

import com.tempo.testapp.exceptions.GenericException;
import com.tempo.testapp.exceptions.UserNotFoundException;
import com.tempo.testapp.entity.User;
import com.tempo.testapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(User user) throws Exception {
        //check here?
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(user.getEmail() !=null){
            user.getEmail().toLowerCase();
        }else{
            throw new GenericException("Falta campo EMAIL");
        }
        if(!pattern.matcher(user.getEmail()).matches())
            throw new GenericException("Email No Valido");

        boolean userExists = checkIfUsernameExists(user.getUser_name());
        boolean emailExists = checkIfEmailExists(user.getEmail());
        System.out.println("username exists? "+userExists);
        System.out.println("email exists? "+emailExists);
        if (userExists)
            throw new GenericException("Nombre de Usuario Ya Existe, intente con otro.");
        if(emailExists)
            throw new GenericException("Correo Electronico Ya Existe, intente con otro.");

        Integer userId = userRepository.createUser(user);
        return userRepository.findById(userId);

    }

    @Override
    public boolean checkIfUsernameExists(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean checkIfEmailExists(String email) throws UserNotFoundException{
        return userRepository.findByUserEmail(email);
    }

    @Override
    public User validateUser(String email, String password) throws GenericException{
        //throw exception if null
        if (email == null)
            throw new GenericException("Campo Email requerido");
        if(password == null)
            throw new GenericException("Campo Password requerido");
        email = email.toLowerCase();

        return userRepository.findByEmailAndPassword(email, password);
    }


}
