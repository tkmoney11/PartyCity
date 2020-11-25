package com.tyler.api.service;

import com.tyler.api.dao.DatabaseRoomDAO;
import com.tyler.api.models.Room;
import com.tyler.api.models.User;

import java.util.Map;

public class RoomService {
    private DatabaseRoomDAO roomDAO = new DatabaseRoomDAO();
    public Room getRoom(int roomId) { return roomDAO.getRoom(roomId); }
    public Map<String, Room> getAllRooms() { return roomDAO.getAllRooms();
    }

    public void addRoom(int hostId, String gameType) {
        roomDAO.addRoom(hostId, gameType);
    }

    public void editHost(int roomId, int newHost) {
        roomDAO.editHost(roomId, newHost);
    }

    public void deleteRoom(int roomId) {
        roomDAO.deleteRoom(roomId);
    }

    public Map<String, Room> getRoomByName(String name) {
        return roomDAO.getRoomByName(name);
    }
}
