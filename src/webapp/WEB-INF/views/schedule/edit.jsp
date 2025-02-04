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
        <form action="${pageContext.request.contextPath}/schedule/save" method="post" class="edit-form">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="mode" value="<%= request.getParameter("mode") %>">
            <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
            
            <div class="form-group">
                <label for="person">어린이:</label>
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

            <!-- 시작 시간 -->
            <div class="form-group">
                <label>시작 시간:</label>
                <div class="time-select-group">
                    <select id="startHour" name="startHour" required>
                        <% for(int hour = 9; hour <= 21; hour++) { %>
                            <option value="<%= String.format("%02d", hour) %>"
                                <%= hour == Integer.parseInt(request.getParameter("time").substring(0, 2)) ? "selected" : "" %>>
                                <%= String.format("%02d", hour) %>시
                            </option>
                        <% } %>
                    </select>
                    <select id="startMinute" name="startMinute" required>
                        <% for(int minute = 0; minute < 60; minute += 10) { %>
                            <option value="<%= String.format("%02d", minute) %>"
                                <%= minute == Integer.parseInt(request.getParameter("time").substring(2)) ? "selected" : "" %>>
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
                                <%= hour == Integer.parseInt(request.getParameter("time").substring(0, 2)) + 1 ? "selected" : "" %>>
                                <%= String.format("%02d", hour) %>시
                            </option>
                        <% } %>
                    </select>
                    <select id="endMinute" name="endMinute" required>
                        <% for(int minute = 0; minute < 60; minute += 10) { %>
                            <option value="<%= String.format("%02d", minute) %>"
                                <%= minute == Integer.parseInt(request.getParameter("time").substring(2)) ? "selected" : "" %>>
                                <%= String.format("%02d", minute) %>분
                            </option>
                        <% } %>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="content">내용:</label>
                <textarea id="content" name="content" required></textarea>
            </div>

            <!-- Add color selection -->
            <div class="form-group">
                <label for="color">색상:</label>
                <select id="color" name="color" required>
                    <option value="blue" ${schedule.color == 'blue' ? 'selected' : ''}>파란색</option>
                    <option value="green" ${schedule.color == 'green' ? 'selected' : ''}>초록색</option>
                    <option value="yellow" ${schedule.color == 'yellow' ? 'selected' : ''}>노란색</option>
                </select>
            </div>

            <div class="button-group">
                <button type="submit" class="btn-primary">저장</button>
                <button type="button" class="btn-secondary" onclick="history.back()">취소</button>
            </div>
        </form>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // 시간 입력 제한 설정
    const timeInputs = document.querySelectorAll('input[type="time"]');
    timeInputs.forEach(input => {
        input.addEventListener('change', function() {
            const time = this.value;
            const minutes = parseInt(time.split(':')[1]);
            if (minutes % 10 !== 0) {
                const roundedMinutes = Math.round(minutes / 10) * 10;
                this.value = time.split(':')[0] + ':' + 
                            (roundedMinutes < 10 ? '0' : '') + roundedMinutes;
            }
        });
    });
});
</script>

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