package com.tempo.testapp.service;

import com.tempo.testapp.entity.User;
import com.tempo.testapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.*;


@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("TEST_ROLE");

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //logic
        //need to use rolls for authorities.. check another way..
        final User user = userRepository.findByUsername(s).get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(authority));
       // return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));

    }
    public List<String> getRolesAsList(Set<Role> roles) {
        List <String> rolesAsList = new ArrayList<String>();
        for(Role role : roles){
           // rolesAsList.add(role.getName());
        }
        return rolesAsList;
    }
    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
    public Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRolesAsList(roles));
        return authList;
    }
}
