package main.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseScheduleDAO {
    protected final String jdbcURL = "jdbc:mariadb://localhost:3306/sdb";
    protected final String jdbcUsername = "sdbuser";
    protected final String jdbcPassword = "1226";
    
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
}