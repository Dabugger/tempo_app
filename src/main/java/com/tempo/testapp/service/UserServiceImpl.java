package com.tempo.testapp.service;

import com.tempo.testapp.exceptions.GenericException;
import com.tempo.testapp.exceptions.UserNotFoundException;
import com.tempo.testapp.entity.User;
import com.tempo.testapp.jwtutil.JwtUtil;
import com.tempo.testapp.model.JwtResponse;
import com.tempo.testapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

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

        boolean userExists = checkIfUsernameExists(user.getUsername());
        boolean emailExists = checkIfEmailExists(user.getEmail());
        System.out.println("username exists? "+userExists);
        System.out.println("email exists? "+emailExists);
        if (userExists)
            throw new GenericException("Nombre de Usuario Ya Existe, intente con otro.");
        if(emailExists)
            throw new GenericException("Correo Electronico Ya Existe, intente con otro.");
        System.out.println("SAVING USER WITH PASS "+user.getPassword());
        User userId = userRepository.save(user);
        return userId;

    }

    @Override
    public boolean checkIfUsernameExists(String username) throws UserNotFoundException {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean checkIfEmailExists(String email) throws UserNotFoundException{
        return userRepository.existsByEmail(email);
    }

    @Override
    public ResponseEntity<?> validateUser(String username, String email, String password) throws GenericException{
        System.out.println("-----------------VALIDATE USER:"+password);
        JwtResponse response = null;
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (BadCredentialsException e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Bad Request.");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);
        System.out.println("JWT TOKEN -> "+jwt);
        //UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();//CANNOT CAST TO....
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        response = new JwtResponse(jwt,
                userDetails.getUsername()
        );
        return ResponseEntity.ok(response);
    }


}
