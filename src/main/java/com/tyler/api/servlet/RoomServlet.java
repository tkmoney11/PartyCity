package com.tyler.api.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tyler.api.models.Room;
import com.tyler.api.service.RoomService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

// TODO CHANGE ROOM SERVICE MODEL TO HOST MANY USERS
// TODO ADD ADMIN FUNCTIONALITY/PRIVILAGES

@WebServlet("/RoomServlet/*")
public class RoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ObjectMapper objectMapper = new ObjectMapper();
    private RoomService roomService = new RoomService();
    private static Logger logger = Logger.getLogger(HttpServlet.class);

    public RoomServlet() {
        super();
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET request invoked");
        HttpSession session = req.getSession(false);
        if (session != null || true) {
            logger.info(session);
            String action = req.getPathInfo() == null ? "" : req.getPathInfo();
            switch (action) {
                case "/all":
                    getAllRooms(req, resp);
                    break;
                case "/showByUsername":
                    showRoomsByUsername(req, resp);
                    break;
                case "/":
                    showRoom(req, resp);
                    break;
                default:
                    resp.getWriter().append("Not an endpoint");
                    resp.setStatus(500);
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
                case "/create":
                    createRoom(req, resp);
                    break;
                case "":
                    createRoom(req, resp);
                    break;
                default:
                    resp.getWriter().append("Not an endpoint");
                    resp.setStatus(500);
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
                case "/editHost":
                    editRoomHost(req, resp);
                    break;
                case "/addUser":
                    addUser(req, resp);
                    break;
                default:
                    resp.getWriter().append("Not an endpoint");
                    resp.setStatus(500);
                    break;
            }
        } else {
            resp.getWriter().append("Request made without login. Please login.");
            resp.setStatus(500);
            logger.info("Request made without login");
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("DELETE request invoked");
        HttpSession session = req.getSession(false);
        if (session != null || true) {
            String action = req.getPathInfo() == null ? "" : req.getPathInfo();
            switch (action) {
                case "/deleteRoom":
                    deleteRoom(req, resp);
                    break;
                case "/removeUser":
                    removeUser(req, resp);
                    break;
                default:
                    resp.getWriter().append("Not an endpoint");
                    resp.setStatus(500);
                    break;
            }
        } else {
            resp.getWriter().append("Request made without login. Please login.");
            resp.setStatus(500);
            logger.info("Request made without login");
        }
    }

    private void showRoom(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int roomId = 0;
        if (req != null) {
            roomId = Integer.parseInt(req.getParameter("id"));
        }

        if (roomId == 0) {
            resp.getWriter().append("Please provide an id");
            resp.setStatus(500);
        } else {
            try {
                String jsonString = objectMapper.writeValueAsString(roomService.getRoom(roomId));
                resp.getWriter().append(jsonString);
                resp.setContentType("application/json");
                resp.setStatus(200);
            } catch (Exception e) {
                resp.getWriter().append(e.toString());
                resp.setStatus(500);
                e.printStackTrace();
            }
        }

    }

    private void getAllRooms(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.getWriter().append(objectMapper.writeValueAsString(roomService.getAllRooms()));
            resp.setContentType("application/json");
            resp.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRoomsByUsername(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        if (username == null) {
            resp.getWriter().append("Please Provide a username");
            resp.setStatus(500);
        } else {
            try {
                String name = req.getParameter("username");
                String jsonString = objectMapper.writeValueAsString(roomService.getRoomByName(name));
                resp.getWriter().append(jsonString);
                resp.setStatus(200);
            } catch (Exception e) {
                resp.getWriter().append(e.toString());
                resp.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    private void createRoom(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = "";
        if ("POST".equalsIgnoreCase(req.getMethod())) {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        if (!body.contains("hostId") || !body.contains("game")) {
            String missingParams = !body.contains("hostId") ? "hostId" : "";
            missingParams += !body.contains("game") ? " and game" : "";
            resp.getWriter().append("Please provide " + missingParams);
            resp.setStatus(500);
        } else {
            Room room = objectMapper.readValue(body, Room.class);
            try {
                Room addedRoom = roomService.createRoom(room);
                if (addedRoom.getUsers().size() > 0) {
                    resp.getWriter().append(objectMapper.writeValueAsString(addedRoom));
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                } else {
                    resp.getWriter().append("Servlet Failure");
                    resp.setStatus(500);
                }
            } catch (Exception e) {
                resp.getWriter().append(e.toString());
                resp.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    // int uid
    // int roomId
    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = "";
        if ("PUT".equalsIgnoreCase(req.getMethod())) {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        if (!body.contains("roomId") || !body.contains("userId")) {
            String missingParams = !body.contains("roomId") ? "roomId" : "";
            missingParams += !body.contains("userId") ? " and userId" : "";
            resp.getWriter().append("Please provide " + missingParams);
            resp.setStatus(500);
        } else {
            try {
                ObjectNode node = objectMapper.readValue(body, ObjectNode.class);
                Room room = roomService.addUser(node);
                resp.getWriter().append(objectMapper.writeValueAsString(room));
                resp.setStatus(200);
            } catch (Exception e) {
                resp.getWriter().append(e.toString());
                resp.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    private void removeUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = "";
        if ("DELETE".equalsIgnoreCase(req.getMethod())) {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        if (!body.contains("roomId") || !body.contains("userId")) {
            String missingParams = !body.contains("roomId") ? "roomId" : "";
            missingParams += !body.contains("userId") ? " and userId" : "";
            resp.getWriter().append("Please provide " + missingParams);
            resp.setStatus(500);
        } else {
            try {
                ObjectNode node = objectMapper.readValue(body, ObjectNode.class);
                Room room = roomService.removeUser(node);
                resp.getWriter().append(objectMapper.writeValueAsString(room));
                resp.setStatus(200);
            } catch (Exception e) {
                resp.getWriter().append(e.toString());
                resp.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    private void deleteRoom(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = "";
        if ("DELETE".equalsIgnoreCase(req.getMethod())) {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }
        if (!body.contains("roomId")) {
            resp.getWriter().append("Please provide a roomId");
        } else {
            try {
                ObjectNode node = objectMapper.readValue(body, ObjectNode.class);
                roomService.deleteRoom(node.get("roomId").intValue());
                resp.getWriter().append("room deleted!");
                resp.setStatus(200);
            } catch (Exception e) {
                resp.getWriter().append("unable to delete room");
                resp.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    private void editRoomHost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = "";
        if ("PUT".equalsIgnoreCase(req.getMethod())) {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        if (!body.contains("roomId") || !body.contains("newHostId")) {
            String missingParams = !body.contains("roomId") ? "roomId" : "";
            missingParams += !body.contains("newHostId") ? " and newHostId" : "";
            resp.getWriter().append("Please provide " + missingParams);
            resp.setStatus(500);
        } else {
            try {
                ObjectNode node = objectMapper.readValue(body, ObjectNode.class);
                int roomId = node.get("roomId").intValue();
                int hostId = node.get("newHostId").intValue();
                Room room = roomService.editHost(roomId, hostId);
                resp.getWriter().append(objectMapper.writeValueAsString(room));
                resp.setStatus(200);
            } catch (Exception e) {
                resp.getWriter().append(e.toString());
                resp.setStatus(500);
                e.printStackTrace();
            }
        }
    }
}
