package main.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseController extends HttpServlet {

    protected void forward(String path, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

    protected void redirect(String path, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(path);
    }

    protected void sendError(HttpServletResponse response, String message)
            throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(message);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    protected String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        return value != null ? value : defaultValue;
    }

    protected void setMessage(HttpServletRequest request, String message) {
        request.setAttribute("message", message);
    }

    protected void setError(HttpServletRequest request, String error) {
        request.setAttribute("error", error);
    }
}