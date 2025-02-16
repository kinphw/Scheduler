package main.model.dao;

import main.model.Schedule;
import java.sql.*;

public class UpdateScheduleDAO extends BaseScheduleDAO {
    
    private static final String UPDATE_SCHEDULE =
            "UPDATE schedules SET person=?, day=?, start_time=?, end_time=?, content=?, color=? WHERE id=?";

    public boolean updateSchedule(Schedule schedule) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SCHEDULE)) {

            preparedStatement.setString(1, schedule.getPerson());
            preparedStatement.setString(2, schedule.getDay());
            preparedStatement.setTime(3, Time.valueOf(schedule.getStartTime()));
            preparedStatement.setTime(4, Time.valueOf(schedule.getEndTime()));
            preparedStatement.setString(5, schedule.getContent());
            preparedStatement.setString(6, schedule.getColor());
            preparedStatement.setInt(7, schedule.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}