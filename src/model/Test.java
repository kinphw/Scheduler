package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void run() {
        System.out.println("Test running...");
    }

    // mysql sdb에 접근해서 schedule 테이블에서 데이터를 select하는 코드
    public void run2() {
        String jdbcURL = "jdbc:mariadb://localhost:3306/sdb";
        String jdbcUsername = "sdbuser";
        String jdbcPassword = "1226";

        String SELECT_SCHEDULES_SQL = """
            SELECT * FROM schedule
        """;

        try (
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCHEDULES_SQL)
        ) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String person = rs.getString("person");
                String time = rs.getString("time");
                String day = rs.getString("day");
                String activity = rs.getString("activity");
                System.out.println(person + " " + time + " " + day + " " + activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

}
