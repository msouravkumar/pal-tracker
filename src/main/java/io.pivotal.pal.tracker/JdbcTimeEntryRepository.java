package io.pivotal.pal.tracker;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository{

    private JdbcTemplate jdbcTemplate;


    public JdbcTimeEntryRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertQuery = "INSERT INTO time_entries (project_id, user_id, date, hours)  VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(insertQuery,RETURN_GENERATED_KEYS);
            ps.setLong(1, timeEntry.getProjectId());
            ps.setLong(2, timeEntry.getUserId());
            ps.setDate(3, Date.valueOf(timeEntry.getDate()));
            ps.setLong(4, timeEntry.getHours());
            return ps;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        timeEntry.setId(id);
        return timeEntry;
    }

    @Override
    public TimeEntry find(Long id) {
            String findQuery = "select * from time_entries where id = ?";
             TimeEntry timeEntry = null;
            try{
                timeEntry = jdbcTemplate.queryForObject(findQuery, new Object[]{id}, new TimeEntryRowMapper());
            }catch( EmptyResultDataAccessException ex){
                return timeEntry;
            }
            return timeEntry;
    }

    @Override
    public List<TimeEntry> list() {
        String findAllQuery = "select * from time_entries";
        List<TimeEntry> timeEntries = jdbcTemplate.query(findAllQuery,new TimeEntryRowMapper());
        return timeEntries;
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        String updateQuery = "update time_entries set project_id=?, user_id=?, date=?, hours=? where id=?";
        jdbcTemplate.update(updateQuery, new Object[]{timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours(), id});
        timeEntry.setId(id);
        return timeEntry;
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = "delete from time_entries where id=?";
       jdbcTemplate.update(deleteQuery, new Object[]{id});

    }
}
