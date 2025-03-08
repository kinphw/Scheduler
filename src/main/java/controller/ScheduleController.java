package controller;

import model.Schedule;
import model.dao.ReadScheduleDAO;
import model.dto.ScheduleDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;

@WebServlet("/schedule/*")
public class ScheduleController extends BaseController {

    private ReadScheduleDAO readScheduleDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        readScheduleDAO = new ReadScheduleDAO();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (src, typeOfSrc, context) ->
                        new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .create();
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
            String schedulesJson = gson.toJson(schedules);

            ScheduleDTO scheduleDTO = new ScheduleDTO(person, schedules, schedulesJson);
            request.setAttribute("data", scheduleDTO);

            forward("/WEB-INF/views/schedule/index.jsp", request, response);
        } else {
            redirect("/", request, response);
        }
    }

}