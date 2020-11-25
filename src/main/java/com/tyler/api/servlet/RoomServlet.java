package com.tyler.api.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyler.api.models.Room;
import com.tyler.api.models.User;
import com.tyler.api.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//TODO turn privates int protecteds
@WebServlet("/RoomServlet/*")
public class RoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ObjectMapper objectMapper = new ObjectMapper();
    private RoomService roomService = new RoomService();

    public RoomServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().append("Served at: ").append(request.getContextPath());
//        String action = request.getParameter("action");
        String action = "";
        if (request.getPathInfo() != null) {
            action = request.getPathInfo();
        }

        switch (action) {
            case "/edit":
                editUserRoomHost(request, response);
                break;
            case "/delete":
                deleteRoom(request, response);
                break;
            case "/all":
                getAllRooms(request, response);
                break;
            case "/add":
                addRoom(request, response);
                break;
            case "/showByName":
                showRoomsByName(request, response);
                break;
            default:
                showRoom(request, response);
                break;
        }
//        String jsonString = objectMapper.writeValueAsString(userService.getAllUsers());
//        response.getWriter().append(jsonString);
//        response.setStatus(200);

    }

    private void showRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int roomId = 0;
        if (request != null) {
            roomId = Integer.parseInt(request.getParameter("id"));
        }

        try {
//            Room room = roomService.getRoom(roomId);
            String jsonString = objectMapper.writeValueAsString(roomService.getRoom(roomId));
            response.getWriter().append(jsonString);
            response.setStatus(200);
        } catch (Exception e) {
            response.getWriter().append("Something went wrong!");
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    private void showRoomsByName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = "";
        if (request != null) {
            name = request.getParameter("name");
        }

        try {
//            Map<String, Room> room = roomService.getRoomByName(name);
            String jsonString = objectMapper.writeValueAsString(roomService.getRoomByName(name).toString());
            response.getWriter().append(jsonString);
            response.setStatus(200);
        } catch (Exception e) {
            response.getWriter().append("Something went wrong!");
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String hostId = request.getParameter("hostId");
        String gameType = request.getParameter("gameType");

        if (hostId == null || gameType == null || hostId.isEmpty() || gameType.isEmpty()) {
            response.getWriter().append("Please provide valid parameters");
            response.setStatus(500);
        } else {
            int actualHostId = Integer.parseInt(hostId);
            try {
                roomService.addRoom(actualHostId, gameType);
                response.getWriter().append("user added!");
                response.setStatus(200);
            } catch (Exception e) {
                response.getWriter().append(e.toString());
                response.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    private void getAllRooms(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().append(roomService.getAllRooms().toString());
        response.setStatus(200);
    }

    private void deleteRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            roomService.deleteRoom(roomId);
            response.getWriter().append("user deleted!");
            response.setStatus(200);
        } catch (Exception e) {
            response.getWriter().append("unable to delete stated user");
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    private void editUserRoomHost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            int newHost = Integer.parseInt(request.getParameter("newHost"));
            roomService.editHost(roomId, newHost);
            response.getWriter().append("password changed!");
        } catch (Exception e) {
            response.getWriter().append("unable to edit password");
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
