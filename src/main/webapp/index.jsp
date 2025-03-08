<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String person = request.getParameter("person");
    if (person == null || person.isEmpty()) {
        person = "gy";  // 기본값
    }
    response.sendRedirect(request.getContextPath() + "/schedule?person=" + person);
%>