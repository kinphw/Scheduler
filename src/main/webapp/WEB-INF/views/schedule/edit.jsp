<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Schedule" %>
<%
    Schedule schedule = (Schedule)request.getAttribute("schedule");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>일정 관리</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<div class="container">
    <div class="edit-container">
        <form action="${pageContext.request.contextPath}/<%= "edit".equals(request.getAttribute("mode")) ? "schedule/update" : "schedule/save" %>" 
              method="post" 
              class="edit-form">
            
            <input type="hidden" name="mode" value="${mode}">
            <input type="hidden" name="id" value="${schedule.id}">
            
            <!-- 기존 폼 필드들 -->
            <div class="form-group">
                <label for="person">어린이:</label>
                <input type="hidden" id="person" name="person" 
                    value="${schedule != null ? schedule.person : param.person}">
                <input type="text" 
                    value="<%= ("gy".equals(request.getParameter("person")) || (schedule != null && "gy".equals(schedule.getPerson()))) ? "건영" : "건우" %>" 
                    readonly>
            </div>

            <div class="form-group">
                <label for="day">요일:</label>
                <select id="day" name="day" required>
                    <option value="월" ${(schedule != null && schedule.day == '월') || param.day == '월' ? 'selected' : ''}>월</option>
                    <option value="화" ${(schedule != null && schedule.day == '화') || param.day == '화' ? 'selected' : ''}>화</option>
                    <option value="수" ${(schedule != null && schedule.day == '수') || param.day == '수' ? 'selected' : ''}>수</option>
                    <option value="목" ${(schedule != null && schedule.day == '목') || param.day == '목' ? 'selected' : ''}>목</option>
                    <option value="금" ${(schedule != null && schedule.day == '금') || param.day == '금' ? 'selected' : ''}>금</option>
                </select>
            </div>




            <!-- 시작 시간 -->
            <div class="form-group">
                <label>시작 시간:</label>
                <div class="time-select-group">
                    <select id="startHour" name="startHour" required>
                        <% for(int hour = 9; hour <= 21; hour++) { %>
                            <option value="<%= String.format("%02d", hour) %>"
                                <%= (schedule != null && hour == schedule.getStartTime().getHour()) || 
                                    (schedule == null && hour == Integer.parseInt(request.getParameter("time").substring(0, 2))) ? "selected" : "" %>>
                                <%= String.format("%02d", hour) %>시
                            </option>
                        <% } %>
                    </select>
                    <select id="startMinute" name="startMinute" required>
                        <% for(int minute = 0; minute < 60; minute += 10) { %>
                            <option value="<%= String.format("%02d", minute) %>"
                                <%= (schedule != null && minute == schedule.getStartTime().getMinute()) || 
                                    (schedule == null && minute == Integer.parseInt(request.getParameter("time").substring(2))) ? "selected" : "" %>>
                                <%= String.format("%02d", minute) %>분
                            </option>
                        <% } %>
                    </select>
                </div>
            </div>

            <!-- 종료 시간 -->
            <div class="form-group">
                <label>종료 시간:</label>
                <div class="time-select-group">
                    <select id="endHour" name="endHour" required>
                        <% for(int hour = 9; hour <= 21; hour++) { %>
                            <option value="<%= String.format("%02d", hour) %>"
                                <%= (schedule != null && hour == schedule.getEndTime().getHour()) || 
                                    (schedule == null && hour == Integer.parseInt(request.getParameter("time").substring(0, 2)) + 1) ? "selected" : "" %>>
                                <%= String.format("%02d", hour) %>시
                            </option>
                        <% } %>
                    </select>
                    <select id="endMinute" name="endMinute" required>
                        <% for(int minute = 0; minute < 60; minute += 10) { %>
                            <option value="<%= String.format("%02d", minute) %>"
                                <%= (schedule != null && minute == schedule.getEndTime().getMinute()) || 
                                    (schedule == null && minute == Integer.parseInt(request.getParameter("time").substring(2))) ? "selected" : "" %>>
                                <%= String.format("%02d", minute) %>분
                            </option>
                        <% } %>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="content">내용:</label>
                <textarea id="content" name="content" required>${schedule != null ? schedule.content : ''}</textarea>
            </div>

            <div class="form-group">
                <label for="color">색상:</label>
                <select id="color" name="color" required>
                    <option value="blue" ${schedule.color == 'blue' ? 'selected' : ''}>파란색</option>
                    <option value="green" ${schedule.color == 'green' ? 'selected' : ''}>초록색</option>
                    <option value="yellow" ${schedule.color == 'yellow' ? 'selected' : ''}>노란색</option>
                </select>
            </div>

            <div class="button-group">
                <% if ("edit".equals(request.getAttribute("mode"))) { %>
                    <button type="submit" class="btn-primary">수정</button>
<%--                    <button type="button" class="btn-danger" onclick="confirmDelete()">삭제</button>--%>
                    <button type="button" class="btn-danger">삭제</button>
                <% } else { %>
                    <button type="submit" class="btn-primary">저장</button>
                <% } %>
                <button type="button" class="btn-secondary" onclick="history.back()">취소</button>
            </div>
        </form>

        <!-- 삭제 처리를 위한 hidden form -->
        <form id="deleteForm" action="${pageContext.request.contextPath}/schedule/delete" method="post" style="display:none;">
            <input type="hidden" name="id" value="${schedule.id}">
            <input type="hidden" name="person" value="${schedule.person}">
        </form>
    </div>
</div>

<script type="module" src="${pageContext.request.contextPath}/js/function/edit.js"></script>

</body>
</html>