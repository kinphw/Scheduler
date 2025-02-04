package main.controller.schedule;

import main.controller.BaseController;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/schedule/form/add")
public class ShowAddFormController extends BaseController {

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