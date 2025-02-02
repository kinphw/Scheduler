package main.controller;

import main.model.Schedule;
import main.model.ScheduleDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.List;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {

    private ScheduleDAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new ScheduleDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String person = request.getParameter("person");
        boolean success = false;
        String message = "";

        try {
            switch (action) {
                case "save":
                    Schedule newSchedule = new Schedule();
                    newSchedule.setPerson(person);
                    newSchedule.setDay(request.getParameter("day"));
                    newSchedule.setStartTime(Time.valueOf(request.getParameter("startTime")));
                    newSchedule.setEndTime(Time.valueOf(request.getParameter("endTime")));
                    newSchedule.setContent(request.getParameter("content"));

                    if (request.getParameter("id") != null) {
                        newSchedule.setId(Integer.parseInt(request.getParameter("id")));
                        success = dao.updateSchedule(newSchedule);
                        message = success ? "수정" : "수정 실패";
                    } else {
                        success = dao.saveSchedule(newSchedule);
                        message = success ? "저장" : "저장 실패";
                    }
                    break;

                case "delete":
                    int id = Integer.parseInt(request.getParameter("id"));
                    success = dao.deleteSchedule(id);
                    message = success ? "삭제" : "삭제 실패";
                    break;

                default:
                    response.sendRedirect("index.jsp");
                    return;
            }

            // 작업 결과에 따른 리다이렉션
            String encodedMessage = java.net.URLEncoder.encode(message, "UTF-8");
            response.sendRedirect("index.jsp?person=" + person + "&result=" + (success ? "success" : "fail") + "&message=" + encodedMessage);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?person=" + person + "&result=fail&message=오류발생");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String person = request.getParameter("person");
        if (person != null) {
            ScheduleDAO dao = new ScheduleDAO();
            List<Schedule> schedules = dao.getSchedulesByPerson(person);
            
            // Convert schedules to JSON manually
            JSONArray jsonArray = new JSONArray();
            for (Schedule schedule : schedules) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id", schedule.getId());
                jsonObj.put("person", schedule.getPerson());
                jsonObj.put("day", schedule.getDay());
                jsonObj.put("startTime", schedule.getStartTime().toString());
                jsonObj.put("endTime", schedule.getEndTime().toString());
                jsonObj.put("content", schedule.getContent());
                jsonArray.add(jsonObj);
            }
            
            request.setAttribute("scheduleJson", jsonArray.toString());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}