<%@ page import="java.util.List" %>
<%@ page import="main.model.Schedule" %>
<%@ page import="main.model.ScheduleDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>일정 관리</title>
    <%
    int a = 10;
    int b = 20;

    %>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>일정 관리</h1>
        <div class="person-selector">
            <a href="index.jsp?person=gy" class="button">건영</a>
            <a href="index.jsp?person=gw" class="button">건우</a>
        </div>
    </div>

    <%
        // 데이터를 로드하는 부분은 모델로 이관되었으므로 여기서는 컨트롤러부터 전달받은 데이터를 사용
        //        String person = request.getParameter("person");
        //        if (person != null) {
        //            ScheduleDAO dao = new ScheduleDAO();
        //            List<Schedule> schedules = dao.getSchedulesByPerson(person);
    %>
    
    <h2><%= person.equals("gy") ? "건영" : "건우" %> 시간표</h2>
    <div class="schedule-grid">
        <table class="schedule-grid">
            <thead>
                <tr>
                    <th class="time-cell">시간</th>
                    <th class="day-cell">월</th>
                    <th class="day-cell">화</th>
                    <th class="day-cell">수</th>
                    <th class="day-cell">목</th>
                    <th class="day-cell">금</th>
                </tr>
            </thead>
            <tbody>
                <% 
                for(int hour = 9; hour <= 21; hour++) {
                    for(int minute = 0; minute < 60; minute += 10) {
                        String formattedTime = String.format("%02d:%02d", hour, minute);
                %>
                    <tr>
                        <td class="time-cell" 
                            data-time="<%= formattedTime %>">
                            <%= formattedTime %>
                        </td>
                        <% 
                        for(String day : new String[]{"월", "화", "수", "목", "금"}) {
                        %>
                            <td class="schedule-cell" 
                                data-day="<%= day %>" 
                                data-time="<%= String.format("%02d%02d", hour, minute) %>">
                            </td>
                        <% } %>
                    </tr>
                <% 
                    }
                } 
                %>
            </tbody>
        </table>
    </div>
    <% } %>
</div>

<script>
// 기본 JavaScript 코드는 나중에 추가
</script>
</body>
</html>