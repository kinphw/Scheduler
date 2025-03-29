<%@ page import="java.util.List" %>
<%@ page import="model.Schedule" %>

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
<%@ page import="model.dto.ScheduleDTO" %>


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
               class="button <%= ((ScheduleDTO)request.getAttribute("data")).getPerson().equals("gy") ? "active" : "" %>">
                건영
            </a>
            <a href="schedule?person=gw"
               class="button <%= ((ScheduleDTO)request.getAttribute("data")).getPerson().equals("gw") ? "active" : "" %>">
                건우
            </a>
            <button id="helpButton" class="button help-button">?</button> <%-- 물음표 버튼 추가 250308  --%>
        </div>
    </div>
<%--    <h2><%= person.equals("gy") ? "건영" : "건우" %> 시간표</h2>--%>
    <div class="schedule-container">
        <div class="schedule-grid">
            <div class="table-container">
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

                <div id="schedule-overlay"></div>
            </div>
        </div>

<%--        <div id="schedule-overlay"></div>--%>
    </div>
</div>

    <script type="module">
        import { renderSchedule } from "${pageContext.request.contextPath}/js/schedule.js";
        import { initializeHelp } from "${pageContext.request.contextPath}/js/function/help.js";

        const schedules = ${data.schedulesJson};// 직접 문자열로 만들지 않고 Gson을 이용해 JSON으로 변환한 문자열을 사용
        const currentPerson = '${data.person}';

        document.addEventListener('DOMContentLoaded', function() {
            initializeHelp();
            schedules
                .filter(schedule => schedule.person === currentPerson)
                .forEach(schedule => renderSchedule(schedule));
        });
        // })();
    </script>
</body>
</html>