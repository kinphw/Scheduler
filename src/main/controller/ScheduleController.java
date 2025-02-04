package main.controller;

import main.model.Schedule;
import main.model.dao.ReadScheduleDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/schedule/*")
public class ScheduleController extends BaseController {

    private ReadScheduleDAO readScheduleDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        readScheduleDAO = new ReadScheduleDAO();
        // scheduleDAO = new TestScheduleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            // null이거나 "/" 인 경우 모두 목록 표시
            if (pathInfo == null || pathInfo.equals("/")) {
                showList(request, response);
            } else { // pathInfo가 의도하지 않은 경우
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) { // 일반 오류처리
            sendError(response, "처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // person 파라미터 인식
        String person = getParameter(request, "person", "");

        // person 파라미터가 있는 경우
        if (!person.isEmpty()) {
            List<Schedule> schedules = readScheduleDAO.getSchedulesByPerson(person);
            request.setAttribute("schedules", schedules); // 전달 : schedules
            request.setAttribute("person", person); // 전달 : person
            forward("/WEB-INF/views/schedule/index.jsp", request, response);
        } else {
            redirect("/", request, response);
        }
    }

}