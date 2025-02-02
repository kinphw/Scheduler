<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>일정 관리</title>
    <style>
        /* ...existing styles... */
    </style>
</head>
<body>
<h1>일정 <%= request.getParameter("mode").equals("edit") ? "수정" : "추가" %></h1>
<form action="ScheduleServlet" method="post">
    <input type="hidden" name="person" value="<%= request.getParameter("person") %>">
    <input type="hidden" name="mode" value="<%= request.getParameter("mode") %>">
    <% if(request.getParameter("id") != null) { %>
    <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
    <% } %>

    <div class="form-group">
        <label for="day">요일:</label>
        <select id="day" name="day" required>
            <option value="">선택하세요</option>
            <option value="월">월</option>
            <option value="화">화</option>
            <option value="수">수</option>
            <option value="목">목</option>
            <option value="금">금</option>
        </select>
    </div>

    <div class="form-group">
        <label for="startTime">시작 시간:</label>
        <select id="startTime" name="startTime" required>
            <%
                for(int hour = 9; hour < 21; hour++) {
                    for(int min = 0; min < 60; min += 10) {
            %>
            <option value="<%= String.format("%02d:%02d:00", hour, min) %>">
                <%= String.format("%02d:%02d", hour, min) %>
            </option>
            <%
                    }
                }
            %>
        </select>
    </div>

    <div class="form-group">
        <label for="endTime">종료 시간:</label>
        <select id="endTime" name="endTime" required>
            <%
                for(int hour = 9; hour < 21; hour++) {
                    for(int min = 0; min < 60; min += 10) {
            %>
            <option value="<%= String.format("%02d:%02d:00", hour, min) %>">
                <%= String.format("%02d:%02d", hour, min) %>
            </option>
            <%
                    }
                }
            %>
        </select>
    </div>

    <div class="form-group">
        <label for="content">내용:</label>
        <input type="text" id="content" name="content" required>
    </div>

    <div class="form-group">
        <input type="submit" name="action" value="save" class="button save" value="저장">
        <% if(request.getParameter("mode").equals("edit")) { %>
        <input type="submit" name="action" value="delete" class="button delete" value="삭제">
        <% } %>
        <button type="button" onclick="location.href='index.jsp?person=<%= request.getParameter("person") %>'"
                class="button cancel">취소</button>
    </div>
</form>

<script>
    // 시작 시간이 종료 시간보다 늦을 수 없도록 체크
    document.getElementById('startTime').addEventListener('change', function() {
        const startTime = this.value;
        const endTimeSelect = document.getElementById('endTime');

        Array.from(endTimeSelect.options).forEach(option => {
            option.disabled = option.value <= startTime;
        });

        if(endTimeSelect.value <= startTime) {
            for(let i = 0; i < endTimeSelect.options.length; i++) {
                if(!endTimeSelect.options[i].disabled) {
                    endTimeSelect.selectedIndex = i;
                    break;
                }
            }
        }
    });

    // 페이지 로드 시 선택된 요일 설정
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        const day = urlParams.get('day');
        if(day) {
            document.getElementById('day').value = day;
        }
    }
</script>
</body>
</html>