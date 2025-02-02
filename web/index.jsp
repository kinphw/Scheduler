<%@ page import="java.util.List" %>
<%@ page import="model.Schedule" %>
<%@ page import="model.ScheduleDAO" %>
<%@ page import="java.sql.Time" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String result = request.getParameter("result");
    String message = request.getParameter("message");
    String decodedmessage = null;
    if (message != null) {
        decodedmessage = java.net.URLDecoder.decode(message, "UTF-8");
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>시간표 관리 프로그램</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .header {
            text-align: center;
            margin-bottom: 20px;
        }
        .person-selector {
            text-align: center;
            margin-bottom: 30px;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            margin: 0 10px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .button:hover {
            background-color: #0056b3;
        }
        .schedule-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .schedule-table th,
        .schedule-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        .schedule-table th {
            background-color: #f8f9fa;
        }
        .time-cell {
            width: 80px;
            background-color: #f8f9fa;
        }
        .schedule-cell {
            cursor: pointer;
            height: 30px;
        }
        .schedule-cell:hover {
            background-color: #f0f0f0;
        }
        .occupied {
            background-color: #e3f2fd;
        }
    </style>

    <script>
        window.onload = function() {
            <% if (result != null && decodedmessage != null) { %>
            alert('<%= decodedmessage %> ' + ':' + (<%= "success".equals(result) %> ? '성공' : '실패'));
            <% } %>
        }
    </script>

</head>
<body>
<div class="container">
    <div class="header">
        <h1>시간표 관리 프로그램</h1>
        <div class="person-selector">
            <a href="index.jsp?person=gy" class="button">건영</a>
            <a href="index.jsp?person=gw" class="button">건우</a>
        </div>
    </div>

    <%
        String person = request.getParameter("person");
        if (person == null || (!person.equals("gy") && !person.equals("gw"))) {
            person = "gy";
        }
        ScheduleDAO dao = new ScheduleDAO();
        List<Schedule> schedules = dao.getSchedulesByPerson(person);
    %>

    <h2><%= person.equals("gy") ? "건영" : "건우" %> 시간표</h2>
    <table class="schedule-table">
        <tr>
            <th>시간</th>
            <th>월</th>
            <th>화</th>
            <th>수</th>
            <th>목</th>
            <th>금</th>
        </tr>

        <%
            for (int hour = 9; hour < 21; hour++) {
                for (int min = 0; min < 60; min += 30) {
                    String timeStr = String.format("%02d:%02d", hour, min);
                    Time currentTime = Time.valueOf(timeStr + ":00");
        %>
        <tr>
            <td class="time-cell"><%= timeStr %></td>
            <%
                String[] days = {"월", "화", "수", "목", "금"};
                for (String day : days) {
                    boolean hasSchedule = false;
                    String content = "";
                    int scheduleId = -1;

                    for (Schedule schedule : schedules) {
                        if (schedule.getDay().equals(day) &&
                                currentTime.compareTo(schedule.getStartTime()) >= 0 &&
                                currentTime.compareTo(schedule.getEndTime()) < 0) {
                            hasSchedule = true;
                            content = schedule.getContent();
                            scheduleId = schedule.getId();
                            break;
                        }
                    }
            %>
            <td class="schedule-cell <%= hasSchedule ? "occupied" : "" %>"
                onclick="location.href='editSchedule.jsp?person=<%= person %>&day=<%= day %>&time=<%= timeStr %>&mode=<%= hasSchedule ? "edit" : "add" %><%= hasSchedule ? "&id=" + scheduleId : "" %>'">
                <%= content %>
            </td>
            <% } %>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</body>
</html>