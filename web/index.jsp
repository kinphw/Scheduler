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
    <link rel="stylesheet" href="css/style.css">    
    <script src="js/utils.js"></script>
    <script src="js/schedule.js"></script>    
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
        List<Schedule> schedules = null;
        if (person != null) {
            ScheduleDAO dao = new ScheduleDAO();
            schedules = dao.getSchedulesByPerson(person);
    %>
    
    <h2><%= person.equals("gy") ? "건영" : "건우" %> 시간표</h2>
    <div id="scheduleContainer">
        <table class="schedule-table">
            <tr>
                <th>시간</th>
                <th>월</th>
                <th>화</th>
                <th>수</th>
                <th>목</th>
                <th>금</th>
            </tr>
            <% for(int hour = 9; hour < 21; hour++) { 
                 for(int min = 0; min < 60; min += 30) { %>
                <tr>
                    <td class="time-cell"><%= String.format("%02d:%02d", hour, min) %></td>
                    <td class="schedule-cell" data-day="월" data-time="<%= String.format("%02d:%02d", hour, min) %>"></td>
                    <td class="schedule-cell" data-day="화" data-time="<%= String.format("%02d:%02d", hour, min) %>"></td>
                    <td class="schedule-cell" data-day="수" data-time="<%= String.format("%02d:%02d", hour, min) %>"></td>
                    <td class="schedule-cell" data-day="목" data-time="<%= String.format("%02d:%02d", hour, min) %>"></td>
                    <td class="schedule-cell" data-day="금" data-time="<%= String.format("%02d:%02d", hour, min) %>"></td>
                </tr>
            <% }} %>
        </table>
    </div>
    <% } %>
</div>

<script>
    // 스케줄 데이터 초기화
    initializeSchedules([
        <% if (person != null && schedules != null && !schedules.isEmpty()) {
            for(Schedule s : schedules) { %>
        {
            id: <%= s.getId() %>,
            day: '<%= s.getDay() %>',
            startTime: '<%= s.getStartTime() %>',
            endTime: '<%= s.getEndTime() %>',
            content: '<%= s.getContent() %>'
        }<%= schedules.indexOf(s) < schedules.size() - 1 ? "," : "" %>
        <% }} %>
    ], '<%= person %>');

    // 페이지 로드 핸들러 설정
    window.onload = () => handlePageLoad('<%= result %>', '<%= decodedmessage %>');
</script>
</body>
</html>