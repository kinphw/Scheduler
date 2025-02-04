package main.controller.schedule.show;

import main.controller.BaseController;
import main.model.Schedule;
// import main.model.ScheduleDAO;
import main.model.dao.ReadScheduleDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/schedule/form/edit")
public class ShowEditFormController extends BaseController {
    
    private ReadScheduleDAO readScheduleDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        readScheduleDAO = new ReadScheduleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String person = getParameter(request, "person", "");
        String day = getParameter(request, "day", "");
        String time = getParameter(request, "time", "");
        String id = getParameter(request, "id", "");

        // ID로 기존 일정 조회
        Schedule schedule = readScheduleDAO.getScheduleById(Integer.parseInt(id));
        
        if (schedule != null) {
            request.setAttribute("schedule", schedule);
            request.setAttribute("mode", "edit");
            forward("/WEB-INF/views/schedule/edit.jsp", request, response);
        } else {
            setError(request, "일정을 찾을 수 없습니다.");
            redirect("/schedule?person=" + person, request, response);
        }
    }
}