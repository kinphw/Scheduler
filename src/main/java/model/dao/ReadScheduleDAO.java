package model.dao;

import model.Schedule;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReadScheduleDAO extends BaseScheduleDAO {
    
    private static final String SELECT_SCHEDULES_BY_PERSON =
            "SELECT * FROM schedules WHERE person = ? ORDER BY day, start_time";
            
    private static final String SELECT_SCHEDULE_BY_ID =
            "SELECT * FROM schedules WHERE id = ?";
            
    public List<Schedule> getSchedulesByPerson(String person) {
        List<Schedule> schedules = new ArrayList<>();
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCHEDULES_BY_PERSON)) {
            
            preparedStatement.setString(1, person);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(rs.getInt("id"));
                schedule.setPerson(rs.getString("person"));
                schedule.setDay(rs.getString("day"));

                // Time -> LocalTime
                Time dbStartTime = rs.getTime("start_time");
                Time dbEndTime = rs.getTime("end_time");
                LocalTime localStartTime = dbStartTime.toLocalTime();
                LocalTime localEndTime = dbEndTime.toLocalTime();

//                schedule.setStartTime(rs.getTime("start_time"));
//                schedule.setEndTime(rs.getTime("end_time"));
                schedule.setStartTime(localStartTime);
                schedule.setEndTime(localEndTime);

                schedule.setContent(rs.getString("content"));
                schedule.setColor(rs.getString("color"));
                schedule.setCreatedAt(rs.getTimestamp("created_at"));
                schedule.setUpdatedAt(rs.getTimestamp("updated_at"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }
    
    public Schedule getScheduleById(int id) {
        Schedule schedule = null;
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCHEDULE_BY_ID)) {
            
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                schedule = new Schedule();
                schedule.setId(rs.getInt("id"));
                schedule.setPerson(rs.getString("person"));
                schedule.setDay(rs.getString("day"));

                // Time -> LocalTime
                Time dbStartTime = rs.getTime("start_time");
                Time dbEndTime = rs.getTime("end_time");
                LocalTime localStartTime = dbStartTime.toLocalTime();
                LocalTime localEndTime = dbEndTime.toLocalTime();

//                schedule.setStartTime(rs.getTime("start_time"));
//                schedule.setEndTime(rs.getTime("end_time"));
                schedule.setStartTime(localStartTime);
                schedule.setEndTime(localEndTime);

                schedule.setContent(rs.getString("content"));
                schedule.setColor(rs.getString("color"));
                schedule.setCreatedAt(rs.getTimestamp("created_at"));
                schedule.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return schedule;
    }
}