package com.tempo.testapp.repositories;

import com.tempo.testapp.exceptions.GenericException;
import com.tempo.testapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl {

    private static final String SQL_CREATE = "INSERT INTO USERS(id, user_name, email, password, name, country) " +
            "VALUES (NEXTVAL('USER_SEQ'), ?, ?, ?, ?, ?)";

    private static final String SQL_COUNT_BY_USERNAME = "SELECT COUNT(*)  FROM USERS WHERE USER_NAME = ?";
    private static final String SQL_COUNT_BY_EMAIL =    "SELECT COUNT(*)  FROM USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID =        "SELECT * FROM USERS WHERE ID = ?";
    private static final String SQL_FIND_BY_EMAIL =     "SELECT * FROM USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_USERNAME =  "SELECT * FROM USERS WHERE USER_NAME = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
    @Override
    public Integer createUser(User user) throws Exception {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,user.getUser_name());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.setString(4,user.getName());
                ps.setString(5, user.getCountry());

                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("id");
        }catch (Exception e){
            throw new Exception("Exception createUser", e);
        }
    }

    @Override
    public boolean findByUsername(String user_name) {

        boolean exists = jdbcTemplate.queryForObject(SQL_COUNT_BY_USERNAME, new Object[]{user_name}, Boolean.class);
        return exists;
    }

    @Override
    public boolean findByUserEmail(String email) {
        boolean existsEmail = jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Boolean.class );
        return existsEmail;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        System.out.println("getting user info by email and pass..."+email+" "+password);
        try{
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            System.out.println("USER INFO? -> "+user);
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            boolean passWordMatch = bCrypt.matches(
                password, user.getPassword()
            );
            System.out.println("password match is "+passWordMatch);
            if(!passWordMatch)
                throw new GenericException("Combinacion EMAIL/PASS Invalido.");
            return user;
        }catch (EmptyResultDataAccessException e){
            throw new GenericException("Combinacion EMAIL/PASS Invalido.");
        }
    }

    @Override
    public User findById(Integer id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, userRowMapper);
    }

    @Override
    public User findByUserName(String username) throws UsernameNotFoundException {

        return jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME, new Object[]{username}, userRowMapper);
    }

    private RowMapper<User> userRowMapper =((rs,rowNum) -> {
        return new User(
                rs.getInt("id"),
                rs.getString("user_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("country")
        );
    });
*/
}
