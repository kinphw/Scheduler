package controller.schedule.show;

import controller.BaseController;
import model.dao.ReadScheduleDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/schedule/form/add")
public class ShowAddFormController extends BaseController {

    private ReadScheduleDAO readScheduleDAO;  // ScheduleDAO -> ReadScheduleDAO (필요한 경우)    

    @Override
    public void init() throws ServletException {
        super.init();
        readScheduleDAO = new ReadScheduleDAO();  // 변경
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String person = getParameter(request, "person", "");
        String day = getParameter(request, "day", "");
        String time = getParameter(request, "time", "");

        request.setAttribute("person", person);
        request.setAttribute("day", day);
        request.setAttribute("time", time);
        request.setAttribute("mode", "add");

        forward("/WEB-INF/views/schedule/edit.jsp", request, response);
    }
}