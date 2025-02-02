<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>일정 관리</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<div class="container">
    <div class="edit-container">
        <form action="schedule" method="post" class="edit-form">
            <input type="hidden" name="mode" value="<%= request.getParameter("mode") %>">
            <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
            
            <div class="form-group">
                <label for="person">담당자:</label>
                <input type="text" id="person" name="person" value="<%= request.getParameter("person") %>" readonly>
            </div>

            <div class="form-group">
                <label for="day">요일:</label>
                <select id="day" name="day" required>
                    <option value="월" <%= "월".equals(request.getParameter("day")) ? "selected" : "" %>>월</option>
                    <option value="화" <%= "화".equals(request.getParameter("day")) ? "selected" : "" %>>화</option>
                    <option value="수" <%= "수".equals(request.getParameter("day")) ? "selected" : "" %>>수</option>
                    <option value="목" <%= "목".equals(request.getParameter("day")) ? "selected" : "" %>>목</option>
                    <option value="금" <%= "금".equals(request.getParameter("day")) ? "selected" : "" %>>금</option>
                </select>
            </div>

            <div class="form-group">
                <label for="startTime">시작 시간:</label>
                <input type="time" id="startTime" name="startTime" 
                       value="<%= request.getParameter("time") %>" 
                       min="09:00" max="21:00" required>
            </div>

            <div class="form-group">
                <label for="endTime">종료 시간:</label>
                <input type="time" id="endTime" name="endTime" 
                       value="<%= request.getParameter("time") %>" 
                       min="09:00" max="21:00" required>
            </div>

            <div class="form-group">
                <label for="content">내용:</label>
                <textarea id="content" name="content" required></textarea>
            </div>

            <div class="button-group">
                <button type="submit" class="btn-primary">저장</button>
                <button type="button" class="btn-secondary" onclick="history.back()">취소</button>
                <% if ("edit".equals(request.getParameter("mode"))) { %>
                    <button type="button" class="btn-danger" onclick="deleteSchedule()">삭제</button>
                <% } %>
            </div>
        </form>
    </div>
</div>
<script src="../../js/edit.js"></script>
<script>
    function deleteSchedule() {
        if (confirm('정말로 삭제하시겠습니까?')) {
            const form = document.querySelector('form');
            form.action = 'schedule?action=delete';
            form.submit();
        }
    }
</script>
</body>
</html>