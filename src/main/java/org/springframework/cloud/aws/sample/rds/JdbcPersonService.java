package org.springframework.cloud.aws.sample.rds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Agim Emruli
 */
@Service
public class JdbcPersonService implements PersonService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPersonService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> all() {
        return this.jdbcTemplate.query("SELECT * FROM Person", new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new Person(resultSet.getString("firstName"), resultSet.getString("lastName"));
            }
        });
    }

    @Override
    @Transactional
    public void store(Person person) {
        this.jdbcTemplate.update("INSERT INTO Person(firstName, lastName) VALUES (?,?)",
                person.getFirstName(), person.getLastName());
    }
}