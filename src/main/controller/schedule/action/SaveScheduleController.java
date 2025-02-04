package main.controller.schedule.action;

import main.controller.BaseController;
import main.model.Schedule;
import main.model.dao.WriteScheduleDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;

@WebServlet("/schedule/save")
public class SaveScheduleController extends BaseController {

    private WriteScheduleDAO writeScheduleDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        writeScheduleDAO = new WriteScheduleDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // 시간 데이터 조합
            String startHour = request.getParameter("startHour");
            String startMinute = request.getParameter("startMinute");
            String endHour = request.getParameter("endHour");
            String endMinute = request.getParameter("endMinute");

            String startTime = String.format("%s:%s:00", startHour, startMinute);
            String endTime = String.format("%s:%s:00", endHour, endMinute);

            Schedule schedule = new Schedule();
            schedule.setPerson(request.getParameter("person"));
            schedule.setDay(request.getParameter("day"));
            schedule.setStartTime(Time.valueOf(startTime));
            schedule.setEndTime(Time.valueOf(endTime));
            schedule.setContent(request.getParameter("content"));
            schedule.setColor(request.getParameter("color"));

            if (writeScheduleDAO.saveSchedule(schedule)) {
                setMessage(request, "일정이 추가되었습니다.");
                redirect("/schedule?person=" + schedule.getPerson(), request, response);
            } else {
                setError(request, "일정 추가 실패");
                forward("/WEB-INF/views/schedule/edit.jsp", request, response);
            }
        } catch (Exception e) {
            setError(request, "처리 중 오류가 발생했습니다: " + e.getMessage());
            forward("/WEB-INF/views/schedule/edit.jsp", request, response);
        }
    }
}