package main.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private final String jdbcURL = "jdbc:mariadb://localhost:3306/sdb";
    private final String jdbcUsername = "sdbuser";
    private final String jdbcPassword = "1226";

    private static final String SELECT_SCHEDULES_BY_PERSON =
            "SELECT * FROM schedules WHERE person = ? ORDER BY day, start_time";

    private static final String INSERT_SCHEDULE =
            "INSERT INTO schedules (person, day, start_time, end_time, content) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_SCHEDULE =
            "UPDATE schedules SET person=?, day=?, start_time=?, end_time=?, content=? WHERE id=?";

    private static final String DELETE_SCHEDULE =
            "DELETE FROM schedules WHERE id=?";

    public List<Schedule> getSchedulesByPerson(String person) {
        List<Schedule> schedules = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCHEDULES_BY_PERSON)) {

            preparedStatement.setString(1, person);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(rs.getInt("id"));
                schedule.setPerson(rs.getString("person"));
                schedule.setDay(rs.getString("day"));
                schedule.setStartTime(rs.getTime("start_time"));
                schedule.setEndTime(rs.getTime("end_time"));
                schedule.setContent(rs.getString("content"));
                schedule.setCreatedAt(rs.getTimestamp("created_at"));
                schedule.setUpdatedAt(rs.getTimestamp("updated_at"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public boolean saveSchedule(Schedule schedule) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SCHEDULE)) {

            preparedStatement.setString(1, schedule.getPerson());
            preparedStatement.setString(2, schedule.getDay());
            preparedStatement.setTime(3, schedule.getStartTime());
            preparedStatement.setTime(4, schedule.getEndTime());
            preparedStatement.setString(5, schedule.getContent());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSchedule(Schedule schedule) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SCHEDULE)) {

            preparedStatement.setString(1, schedule.getPerson());
            preparedStatement.setString(2, schedule.getDay());
            preparedStatement.setTime(3, schedule.getStartTime());
            preparedStatement.setTime(4, schedule.getEndTime());
            preparedStatement.setString(5, schedule.getContent());
            preparedStatement.setInt(6, schedule.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSchedule(int id) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SCHEDULE)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}