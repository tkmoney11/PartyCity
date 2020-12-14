package com.tyler.api.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tyler.api.models.User;
import com.tyler.api.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// TODO Finish fleshing out documentation
// TODO Unit Testing
// TODO edit/delete users only if you are the user
// TODO USER/ADMIN
    // TODO USER
        // getUser - username, firstname
        // getAllUsers - username, firstname
        // PUT - session-username edit your own info only
        // POST - no admin necessary
        // DELETE - session-username deletion only
// TODO 

/**
 * Servlet implementation class HelloServlet
 */
//@WebServlet("/UserServlet/*")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService = new UserService();
    private static Logger logger = Logger.getLogger(HttpServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() { super(); }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET request invoked");
        HttpSession session = req.getSession(false);
        if (session != null ) {
            logger.info(session);
            String action = req.getPathInfo() == null ? "" : req.getPathInfo();
            switch (action) {
                case "/all":
                    getAllUsers(req, resp);
                    break;
                case "":
                    showUser(req, resp);
                    break;
                case "/":
                    showUser(req, resp);
                    break;
                default:
                    resp.getWriter().append("Not a valid endpoint");
                    break;
            }
        } else {
            resp.getWriter().append("Request made without login. Please login.");
            resp.setStatus(500);
            logger.info("Request made without login");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("POST request invoked");
        HttpSession session = req.getSession(false);
        if (session != null || true) {
            String action = req.getPathInfo() == null ? "" : req.getPathInfo();
            switch (action) {
                case "/add":
                    addUser(req, resp);
                    break;
                case "":
                    addUser(req, resp);
                    break;
                default:
                    resp.getWriter().append("Not a valid endpoint");
                    break;
            }
        } else {
            resp.getWriter().append("Request made without login. Please login.");
            resp.setStatus(500);
            logger.info("Request made without login");
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("PUT request invoked");
        HttpSession session = req.getSession(false);
        if (session != null || true) {
            String action = req.getPathInfo() == null ? "" : req.getPathInfo();
            switch (action) {
                case "/editPass":
                    editUserPassword(req, resp, session);
                    break;
                case "/flipAdmin":
                    flipAdmin(req, resp, session);
                    break;
            }
        } else {
            resp.getWriter().append("Request made without login. Please login.");
            resp.setStatus(500);
            logger.info("Request made without login");
        }
    }

    private void flipAdmin(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException {
        String body = "";
        if ("PUT".equalsIgnoreCase(req.getMethod())) {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        if (!body.contains("id")) {
            resp.getWriter().append("Please provide an id");
            resp.setStatus(500);
        } else {
            try {
                ObjectNode node = objectMapper.readValue(body, ObjectNode.class);
                User user = userService.flipAdmin(node.get("id").intValue());
                resp.getWriter().append(objectMapper.writeValueAsString(user));
                resp.setStatus(200);
                resp.setContentType("application/json");
                session.removeAttribute("user");
                session.setAttribute("user", user);
            } catch (Exception e) {
                resp.getWriter().append(e.toString());
                resp.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("DELETE request invoked");
        HttpSession session = req.getSession(false);
        if (session != null || true) {
            String action = req.getPathInfo() == null ? "" : req.getPathInfo();
            switch (action) {
                case "/delete":
                    deleteUser(req, resp, session);
                    break;
                case "":
                    deleteUser(req, resp, session);
                    break;
            }
        } else {
            resp.getWriter().append("Request made without login. Please login.");
            resp.setStatus(500);
            logger.info("Request made without login");
        }
    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     * Shows User in JSON format
     */
    private void showUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("GET Request made it to " + req.getRequestURI());
        int userId = 0;
        if (req != null) {
            userId = Integer.parseInt(req.getParameter("id"));
            System.out.println(userId);
        }

        try {
            String jsonString = objectMapper.writeValueAsString(userService.getUser(userId));
            resp.getWriter().append(jsonString);
            resp.setContentType("application/json");
            resp.setStatus(200);
        } catch (Exception e) {
            resp.getWriter().append(e.toString());
            resp.setStatus(500);
            e.printStackTrace();
        }
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET Request made it to " + request.getRequestURI());
        response.getWriter().append(objectMapper.writeValueAsString(userService.getAllUsers()));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    //TODO read from body not params
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {
        logger.debug("DELETE Request made it to " + req.getRequestURI());
        String body = "";
        if ("DELETE".equalsIgnoreCase(req.getMethod())) {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        try {
            ObjectNode node = objectMapper.readValue(body, ObjectNode.class);
            int id = node.get("id").intValue();
            User sessionUser = (User) session.getAttribute("user");
            if (sessionUser.isAdministrator() || sessionUser.getId() == id) {
                userService.deleteUser(id);
                resp.getWriter().append("user deleted! (Whether or not user existed)");
                resp.setStatus(200);
            } else {
                resp.getWriter().append("You are not authorized to delete this user");
                resp.setStatus(500);
            }
        } catch (Exception e) {
            resp.getWriter().append("unable to delete user");
            resp.setStatus(500);
            e.printStackTrace();
        }
    }

    private void editUserPassword(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {
        logger.debug("PUT Request made it to " + req.getRequestURI());
        String body = "";
        if ("PUT".equalsIgnoreCase(req.getMethod())) {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        if (!body.contains("newPassword") || !body.contains("id")) {
            String missingParams = !body.contains("newPassword") ? "newPassword" : "";
            missingParams += !body.contains("id") ? " and id" : "";
            resp.getWriter().append("Please provide " + missingParams);
            resp.setStatus(500);
        } else {
            try {
                ObjectNode node = objectMapper.readValue(body, ObjectNode.class);
                int id = node.get("id").intValue();
                User sessionUser = (User) session.getAttribute("user");
                System.out.println(sessionUser);
                // can only edit user password if session user is the same as edit user id
                // OR if session user is administrator
                if (id == sessionUser.getId() || sessionUser.isAdministrator()) {
                    String newPassword = node.get("newPassword").toString().replace("\"", "");
                    User user = userService.editUserPassword(id, newPassword);
                    resp.getWriter().append(objectMapper.writeValueAsString(user));
                    resp.setContentType("applications/json");
                    resp.setStatus(200);
                } else {
                    resp.getWriter().append("You are not authorized to edit this users password");
                    resp.setStatus(500);
                }

            } catch (Exception e) {
                resp.getWriter().append(e.toString());
                resp.setStatus(500);
                e.printStackTrace();
            }
        }

    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("POST Request made it to " + req.getRequestURI());

        // Reading and storing body of POST request through raw data
        // SEE LoginServlet.java for how to read ASCII input from form-data
        User user = new User();
        try {
            String body = "";
            if ("POST".equalsIgnoreCase(req.getMethod())) {
                body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            }

            user = objectMapper.readValue(body, User.class);
        } catch (Exception e) {
            resp.getWriter().append("invalid body. ");
            logger.info(e);
        }

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String username =  user.getUsername();
        String password = user.getPassword();

        if (firstName == null || lastName == null || email == null || username == null || password == null ||
            firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            resp.getWriter().append("Please provide valid parameters");
            resp.setStatus(500);
        } else {
            try {

                resp.getWriter().append(objectMapper.writeValueAsString(userService.addUser(firstName, lastName, email, username, password)));
                resp.setContentType("application/json");
                resp.setStatus(200);
            } catch (Exception e) {
                resp.getWriter().append(e.toString());
                resp.setStatus(500);
                logger.info(e);
            }
        }
    }
}