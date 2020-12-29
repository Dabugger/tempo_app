package com.tempo.testapp.repositories;

import com.tempo.testapp.entity.HistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {

    private String SQL_INSERT_HISTORY =     "INSERT INTO HISTORY(query_id, endpoint, user_name, number1, number2)" +
            "VALUES (NEXTVAL('history_seq'), ?, ?, ?, ?)";

    private static final String SQL_CREATE = "INSERT INTO USERS(id, user_name, email, password, name, country) " +
            "VALUES (NEXTVAL('USER_SEQ'), ?, ?, ?, ?, ?)";

    private static final String SQL_PAGINATE_BY_SIZE = "SELECT * FROM HISTORY LIMIT %s OFFSET %s";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void addHistoryQuery(String endpoint, String username, int n1, int n2) {
        System.out.println("JDBC TEMPLATE INSERT");

        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_HISTORY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, endpoint);
                ps.setString(2, username);
                ps.setInt(3, n1);
                ps.setInt(4, n2);
                return ps;
            }, keyHolder);
        }catch (EmptyResultDataAccessException e){
            System.out.println(e.toString());
        }


    }

    @Override
    public List<HistoryQuery> getPagedHistory(int size, int page) {
        List<HistoryQuery> hist = jdbcTemplate.query(
                String.format(SQL_PAGINATE_BY_SIZE, size, page),
                (rs, rowNum) -> new HistoryQuery(
                        rs.getInt("query_id"),
                        rs.getString("endpoint"),
                        rs.getString("user_name"),
                        rs.getInt("number1"),
                        rs.getInt("number2")));

        return hist;
    }

    @Override
    public Iterable<HistoryQuery> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<HistoryQuery> findAll(Pageable pageable) {

        return null;
    }

    @Override
    public <S extends HistoryQuery> S save(S s) {
        return null;
    }

    @Override
    public <S extends HistoryQuery> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<HistoryQuery> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<HistoryQuery> findAll() {
        return null;
    }

    @Override
    public Iterable<HistoryQuery> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(HistoryQuery historyQuery) {

    }

    @Override
    public void deleteAll(Iterable<? extends HistoryQuery> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    private RowMapper<HistoryQuery> userRowMapper =((rs, rowNum) -> {
        return new HistoryQuery(
                rs.getInt("query_id"),
                rs.getString("endpoint"),
                rs.getString("user_name"),
                rs.getInt("number1"),
                rs.getInt("number2")
        );
    });
}
