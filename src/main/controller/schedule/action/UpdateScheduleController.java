package main.controller.schedule.action;

import main.controller.BaseController;
import main.model.Schedule;
import main.model.dao.UpdateScheduleDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;

@WebServlet("/schedule/update")
public class UpdateScheduleController extends BaseController {
    
    private UpdateScheduleDAO updateScheduleDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        updateScheduleDAO = new UpdateScheduleDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {

            Schedule schedule = new Schedule();
            schedule.setId(Integer.parseInt(request.getParameter("id")));
            schedule.setPerson(request.getParameter("person"));
            schedule.setDay(request.getParameter("day"));

            // 시간 데이터 조합
            String startHour = request.getParameter("startHour");
            String startMinute = request.getParameter("startMinute");
            String endHour = request.getParameter("endHour");
            String endMinute = request.getParameter("endMinute");

            String startTime = String.format("%s:%s:00", startHour, startMinute);
            String endTime = String.format("%s:%s:00", endHour, endMinute);

            Time start = Time.valueOf(startTime);
            Time end = Time.valueOf(endTime);
            
            if (start.compareTo(end) >= 0) {
                setError(request, "종료 시간은 시작 시간보다 늦어야 합니다.");
                request.setAttribute("schedule", schedule);
                forward("/WEB-INF/views/schedule/edit.jsp", request, response);
                return;
            }
    
            schedule.setStartTime(start);
            schedule.setEndTime(end);
            schedule.setContent(request.getParameter("content"));
            schedule.setColor(request.getParameter("color"));

            if (updateScheduleDAO.updateSchedule(schedule)) {
                setMessage(request, "일정이 수정되었습니다.");
                redirect("/schedule?person=" + schedule.getPerson(), request, response);
            } else {
                setError(request, "일정 수정 실패");
                forward("/WEB-INF/views/schedule/edit.jsp", request, response);
            }
        } catch (Exception e) {
            setError(request, "처리 중 오류가 발생했습니다: " + e.getMessage());
            forward("/WEB-INF/views/schedule/edit.jsp", request, response);
        }
    }
}