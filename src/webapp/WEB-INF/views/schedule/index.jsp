<%@ page import="java.util.List" %>
<%@ page import="main.model.Schedule" %>

<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.JsonElement" %>
<%@ page import="com.google.gson.JsonPrimitive" %>
<%@ page import="com.google.gson.JsonSerializationContext" %>
<%@ page import="com.google.gson.JsonSerializer" %>
<%@ page import="java.lang.reflect.Type" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>일정 관리</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <div class="header">
<%--        <h1>일정 관리</h1>--%>
        <div class="person-selector">
            <a href="schedule?person=gy"
               class="button <%= request.getAttribute("person") != null && request.getAttribute("person").equals("gy") ? "active" : "" %>">
                건영
            </a>
            <a href="schedule?person=gw"
               class="button <%= request.getAttribute("person") != null && request.getAttribute("person").equals("gw") ? "active" : "" %>">
                건우
            </a>
        </div>

    <%
        String person = (String) request.getAttribute("person");
        List<Schedule> schedules = (List<Schedule>) request.getAttribute("schedules");

        // Gson 객체 생성
        // Gson gson = new Gson();
        // Gson 빌더로 24시간 포맷 지정
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(java.time.LocalTime.class, new JsonSerializer<LocalTime>() {
                @Override
                public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }
            })
            .create();

        String schedulesJson = gson.toJson(schedules);

        if (person != null && schedules != null) {
    %>

<%--    <h2><%= person.equals("gy") ? "건영" : "건우" %> 시간표</h2>--%>
    <div class="schedule-container">
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
                    %>
                    <tr>
                        <td class="time-cell" rowspan="6" data-time="<%= String.format("%02d:00", hour) %>">
                            <%= String.format("%02d:00", hour) %>
                        </td>
                        <% for(String day : new String[]{"월", "화", "수", "목", "금"}) { %>
                        <td class="schedule-cell hour-start"
                            data-day="<%= day %>"
                            data-time="<%= String.format("%02d00", hour) %>"
                            id="cell-<%= day %>-<%= String.format("%02d00", hour) %>">
                        </td>
                        <% } %>
                    </tr>
                    <%
                        for(int minute = 10; minute < 60; minute += 10) {
                    %>
                    <tr>
                        <% for(String day : new String[]{"월", "화", "수", "목", "금"}) { %>
                        <td class="schedule-cell minute-line"
                            data-day="<%= day %>"
                            data-time="<%= String.format("%02d%02d", hour, minute) %>"
                            id="cell-<%= day %>-<%= String.format("%02d%02d", hour, minute) %>">
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

        <div id="schedule-overlay"></div>
    </div>
    <% } %>
</div>

    <script type="module">
        import { renderSchedule } from "${pageContext.request.contextPath}/js/schedule.js";
        
        // (function() {
        <%--const schedules = [--%>
        <%--    <% for(Schedule schedule : schedules) { %>--%>
        <%--    {--%>
        <%--        day: '<%= schedule.getDay() %>',--%>
        <%--        startTime: '<%= schedule.getStartTime() %>',--%>
        <%--        endTime: '<%= schedule.getEndTime() %>',--%>
        <%--        content: '<%= schedule.getContent() %>',--%>
        <%--        id: <%= schedule.getId() %>,--%>
        <%--        person: '<%= schedule.getPerson() %>',--%>
        <%--        color: '<%= schedule.getColor() %>'--%>
        <%--    },--%>
        <%--    <% } %>--%>
        <%--];--%>

        const schedules = <%= schedulesJson %>; // 직접 문자열로 만들지 않고 Gson을 이용해 JSON으로 변환한 문자열을 사용
        const currentPerson = '<%= person %>';

        document.addEventListener('DOMContentLoaded', function() {
            schedules
                .filter(schedule => schedule.person === currentPerson)
                .forEach(schedule => renderSchedule(schedule));
        });
        // })();
    </script>
</div>
</body>
</html>