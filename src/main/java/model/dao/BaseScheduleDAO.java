package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseScheduleDAO {

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // 드라이버 로드 실패 시 적절한 예외 처리를 합니다.
            throw new ExceptionInInitializerError(e);
        }
    }

//    protected final String jdbcURL = "jdbc:mariadb://localhost:3306/sdb";
    protected final String jdbcURL = "jdbc:mariadb://localhost:3306/sdb?allowPublicKeyRetrieval=true&useSSL=false";

    protected final String jdbcUsername = "sdbuser";
    protected final String jdbcPassword = "1226";
    
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
}