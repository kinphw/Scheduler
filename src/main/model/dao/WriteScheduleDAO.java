package main.model.dao;

import main.model.Schedule;
import java.sql.*;

public class WriteScheduleDAO extends BaseScheduleDAO {
    
    private static final String INSERT_SCHEDULE =
            "INSERT INTO schedules (person, day, start_time, end_time, content, color) VALUES (?, ?, ?, ?, ?, ?)";

    public boolean saveSchedule(Schedule schedule) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SCHEDULE)) {

            preparedStatement.setString(1, schedule.getPerson());
            preparedStatement.setString(2, schedule.getDay());
            preparedStatement.setTime(3, schedule.getStartTime());
            preparedStatement.setTime(4, schedule.getEndTime());
            preparedStatement.setString(5, schedule.getContent());
            preparedStatement.setString(6, schedule.getColor());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}