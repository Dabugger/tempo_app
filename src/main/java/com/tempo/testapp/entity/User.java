package com.tempo.testapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private int id;
    private String user_name;
    private String email;
    private String password;
    private String name;
    private String country;

    public User(String user_name) {
        this.user_name = user_name;
    }
}
