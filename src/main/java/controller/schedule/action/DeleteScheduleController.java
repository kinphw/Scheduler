package controller.schedule.action;

import controller.BaseController;
import model.dao.DeleteScheduleDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/schedule/delete")
public class DeleteScheduleController extends BaseController {
    
    private DeleteScheduleDAO deleteScheduleDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        deleteScheduleDAO = new DeleteScheduleDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String person = request.getParameter("person");

            if (deleteScheduleDAO.deleteSchedule(id)) {
                setMessage(request, "일정이 삭제되었습니다.");
                redirect("/schedule?person=" + person, request, response);
            } else {
                setError(request, "일정 삭제 실패");
                forward("/WEB-INF/views/schedule/edit.jsp", request, response);
            }
        } catch (Exception e) {
            setError(request, "처리 중 오류가 발생했습니다: " + e.getMessage());
            forward("/WEB-INF/views/schedule/edit.jsp", request, response);
        }
    }
}