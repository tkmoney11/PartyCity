package com.tyler.api.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyler.api.models.User;
import com.tyler.api.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@WebServlet("/LoginServlet/*")
public class LoginServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(HttpServlet.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static UserService userService = new UserService();

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("Please make a POST request instead");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = "User";
        if (req.getPathInfo() != null) {
            action = req.getPathInfo();
        }

        // Reading and converting body of request into String
        // source: https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
        String body = "";
        if ("POST".equalsIgnoreCase(req.getMethod())) {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        // Create session
        HttpSession session = req.getSession();

        // CODE SNIPPET TO EXTRACT BODY FROM FORM-DATA
        // Map field-value pairs for user.
        // Stored in userFields
//        System.out.println(body);
//        String lines[] = body.split("\\r?\\n");
//        Map<String, String> userFields = new HashMap<>();
//        for (int i = 1; i < lines.length; i += 4) {
//            userFields.put(lines[i].substring(lines[i].indexOf("\"") + 1, lines[i].indexOf("\"", lines[i].indexOf("\"") + 1)), lines[i + 2]);
//        }

        // TODO actually login
        // Try Logging in
        try {
            // TODO test this more thoroughly
            User userLogin = objectMapper.readValue(body, User.class);
            String username = null;
            String password = null;
            boolean isAuthenticated = false;
            // if username and password are non-null from body
            if (userLogin.getUsername() != null && userLogin.getPassword() != null) {
                username = userLogin.getUsername();
                password = userLogin.getPassword();
                isAuthenticated = userService.authenticateVersionOne(userLogin.getUsername(), userLogin.getPassword());
                session.setAttribute("username", username);
                session.setAttribute("password", password);
            // username and/or password is null
            } else {
                // See if information is already stored in the session and authenticate
                try {
                    username = (String) session.getAttribute("username");
                    password = (String) session.getAttribute("password");
                    isAuthenticated = userService.authenticateVersionOne(username, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            logger.debug("LOGIN ATTEMPT made at " + req.getRequestURI());

            if (isAuthenticated) {
                User user = userService.getUser(username);
                session.setAttribute("user", user);
                System.out.println(session.getAttribute("user"));
                resp.getWriter().append("YOU LOGGED IN as a " + action);
                resp.setStatus(200);
                logger.info(session);
            } else {
                resp.getWriter().append("INCORRECT LOGIN");
                session.invalidate();
                resp.setStatus(401);
            }

        // Login failed
        } catch (Exception e) {
            session.invalidate();
            resp.getWriter().append(e.toString());
            resp.setStatus(400);
            logger.info(e);
        }
    }
}
