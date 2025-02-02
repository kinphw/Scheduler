package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dbtest")
public class DBTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // MariaDB 접속 정보
    private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/sdb";
    private static final String JDBC_USER = "root"; // XAMPP 기본 계정
    private static final String JDBC_PASSWORD = ""; // 기본 비밀번호 (설정에 따라 다를 수 있음)

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // JDBC 드라이버 로드
            Class.forName("org.mariadb.jdbc.Driver");

            // 데이터베이스 연결
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOW() AS current_time");

            out.println("<html><body>");
            out.println("<h2>MariaDB Connection Test</h2>");
            if (rs.next()) {
                out.println("<p>Database connected successfully!</p>");
                out.println("<p>Current Time from DB: " + rs.getString("current_time") + "</p>");
            }
            out.println("</body></html>");

            // 리소스 정리
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Database connection failed!</p>");
            out.println("<pre>" + e.getMessage() + "</pre>");
        }
    }
}
