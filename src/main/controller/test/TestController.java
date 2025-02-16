package main.controller.test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.gson.Gson;

@WebServlet("/test/")
public class TestController extends HttpServlet {

    // TestObject를 클래스 레벨로 이동
    public static class TestObject {
        private String message;
        private int number;

        public TestObject(String message, int number) {
            this.message = message;
            this.number = number;
        }

        // Getter/Setter 추가
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }
    }


    // get을 받는 doGet메서드 구현. 테스트 메시지를 response에 출력하는 기능을 구현.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");  // content-type 수정

        TestObject testObject = new TestObject("Hello, Test!", 123);
        Gson gson = new Gson();
        String json = gson.toJson(testObject);
        
        response.getWriter().write(json);

    }
}
