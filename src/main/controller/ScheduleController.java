package main.controller;

import main.model.Schedule;
import main.model.ScheduleDAO;
import main.model.TestScheduleDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.List;

@WebServlet("/schedule/*")
public class ScheduleController extends BaseController {

    private ScheduleDAO scheduleDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // scheduleDAO = new ScheduleDAO();
        scheduleDAO = new TestScheduleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {

            // null이거나 "/" 인 경우 모두 목록 표시
            if (pathInfo == null || pathInfo.equals("/")) {
                showList(request, response);

            } else if (pathInfo.equals("/edit")) { // edit인 경우
                showEditForm(request, response);

            } else { // pathInfo가 의도하지 않은 경우
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) { // 일반 오류처리
            sendError(response, "처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String person = request.getParameter("person");

        try {
            switch (action) {
                case "save":
                    handleSave(request, response);
                    break;
                case "delete":
                    handleDelete(request, response);
                    break;
                default:
                    redirect("/schedule?person=" + person, request, response);
                    break;
            }
        } catch (Exception e) {
            sendError(response, "처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // person 파라미터 인식
        String person = getParameter(request, "person", "");

        // person 파라미터가 있는 경우
        if (!person.isEmpty()) {
            List<Schedule> schedules = scheduleDAO.getSchedulesByPerson(person);
            request.setAttribute("schedules", schedules); // 전달 : schedules
            request.setAttribute("person", person); // 전달 : person
            forward("/WEB-INF/views/schedule/index.jsp", request, response);
        } else {
            redirect("/", request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forward("/WEB-INF/views/schedule/edit.jsp", request, response);
    }

    private void handleSave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Schedule schedule = new Schedule();
        schedule.setPerson(request.getParameter("person"));
        schedule.setDay(request.getParameter("day"));
        schedule.setStartTime(Time.valueOf(request.getParameter("startTime") + ":00"));
        schedule.setEndTime(Time.valueOf(request.getParameter("endTime") + ":00"));
        schedule.setContent(request.getParameter("content"));

        boolean success;
        String message;

        if (request.getParameter("id") != null) {
            schedule.setId(Integer.parseInt(request.getParameter("id")));
            success = scheduleDAO.updateSchedule(schedule);
            message = "일정이 수정되었습니다.";
        } else {
            success = scheduleDAO.saveSchedule(schedule);
            message = "일정이 저장되었습니다.";
        }

        if (success) {
            setMessage(request, message);
            redirect("/schedule?person=" + schedule.getPerson(), request, response);
        } else {
            setError(request, "저장 실패");
            showEditForm(request, response);
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String person = request.getParameter("person");

        if (scheduleDAO.deleteSchedule(id)) {
            setMessage(request, "일정이 삭제되었습니다.");
        } else {
            setError(request, "삭제 실패");
        }

        redirect("/schedule?person=" + person, request, response);
    }
}