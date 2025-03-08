package model.dao;

import java.sql.*;

public class DeleteScheduleDAO extends BaseScheduleDAO {
    
    private static final String DELETE_SCHEDULE =
            "DELETE FROM schedules WHERE id = ?";

    public boolean deleteSchedule(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SCHEDULE)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}