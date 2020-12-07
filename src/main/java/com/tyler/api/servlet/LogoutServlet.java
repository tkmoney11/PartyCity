package com.tyler.api.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            session.invalidate();
            resp.getWriter().append("Successfully logged out");
            resp.setStatus(200);
        } catch (Exception e) {
            resp.getWriter().append("Server issues");
            resp.setStatus(500);
        }
    }
}
