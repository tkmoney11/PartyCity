package com.tyler.api.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyler.api.models.User;
import com.tyler.api.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/UserServlet/*")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService = new UserService();
    private static Logger logger = Logger.getLogger(HttpServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = "";
        if (request.getPathInfo() != null) {
            action = request.getPathInfo();
        }

        switch (action) {
            case "/edit":
                editUserPassword(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
            case "/all":
                getAllUsers(request, response);
                break;
            case "/add":
                addUser(request, response);
                break;
            default:
                showUser(request, response);
                break;
        }
//        String jsonString = objectMapper.writeValueAsString(userService.getAllUsers());
//        response.getWriter().append(jsonString);
//        response.setStatus(200);

    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("POST Request made it to " + request.getRequestURI());
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            userService.deleteUser(id);
            response.getWriter().append("user deleted!");
            response.setStatus(200);
        } catch (Exception e) {
            response.getWriter().append("unable to delete stated user");
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    private void editUserPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("POST Request made it to " + request.getRequestURI());
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String newPassword = request.getParameter("newPassword");
            userService.editUserPassword(id, newPassword);
            response.getWriter().append("password changed!");
        } catch (Exception e) {
            response.getWriter().append("unable to edit password");
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("GET Request made it to " + request.getRequestURI());
        response.getWriter().append(userService.getAllUsers().toString());
        response.setStatus(200);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("POST Request made it to " + request.getRequestURI());
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (firstName == null || lastName == null || email == null || username == null || password == null ||
            firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            response.getWriter().append("Please provide valid parameters");
            response.setStatus(500);
        } else {
            try {
                userService.addUser(firstName, lastName, email, username, password);
                response.getWriter().append("user added!");
                response.setStatus(200);
            } catch (Exception e) {
                response.getWriter().append(e.toString());
                response.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * Shows User in JSON format
     */
    private void showUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("GET Request made it to " + request.getRequestURI());
        int userId = 0;
        if (request != null) {
            userId = Integer.parseInt(request.getParameter("id"));
            System.out.println(userId);
        }

        try {
            User user = userService.getUser(userId);
            String jsonString = objectMapper.writeValueAsString(userService.getUser(userId));
            response.getWriter().append(jsonString);
            response.setStatus(200);
        } catch (Exception e) {
            response.getWriter().append("Something went wrong!");
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}