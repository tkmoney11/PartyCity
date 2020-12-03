package com.tyler.api.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tyler.api.dao.DatabaseRoomDAO;
import com.tyler.api.dao.DatabaseUserDAO;
import com.tyler.api.exceptions.RoomNotFoundException;
import com.tyler.api.exceptions.SameHostException;
import com.tyler.api.exceptions.UserNotFoundException;
import com.tyler.api.models.Room;
import com.tyler.api.models.User;

import java.util.Map;

public class RoomService {
    private DatabaseRoomDAO roomDAO = new DatabaseRoomDAO();
    private DatabaseUserDAO userDAO = new DatabaseUserDAO();

    public Room getRoom(int roomId) throws RoomNotFoundException { return roomDAO.getRoom(roomId); }
    public Map<Integer, Room> getAllRooms() { return roomDAO.getAllRooms();
    }

    public Room createRoom(Room room) throws UserNotFoundException {
        // TODO factor addToRoomUsers into "addRoom"
        Room tmpRoom = roomDAO.addRoom(room);
        return roomDAO.addToRoomsUsers(tmpRoom);
    }

    public Room editHost(int roomId, int hostId) throws RoomNotFoundException, UserNotFoundException, SameHostException {
        Room room = roomDAO.getRoom(roomId);
        if (room.getHostId() == hostId)
            throw new SameHostException();
        userDAO.getUser(hostId);
        return roomDAO.editHost(roomId, hostId);
    }

    public void deleteRoom(int roomId) throws RoomNotFoundException {
        roomDAO.getRoom(roomId);
        roomDAO.deleteRoom(roomId);
    }

    public Map<Integer, Room> getRoomByName(String name) throws UserNotFoundException {
        return roomDAO.getRoomByName(name);
    }

    public Room addUser(ObjectNode node) throws UserNotFoundException, RoomNotFoundException {
        userDAO.getUser(node.get("userId").intValue());
        roomDAO.getRoom(node.get("roomId").intValue());
        return roomDAO.addUser(node);
    }

    public Room removeUser(ObjectNode node) throws UserNotFoundException, RoomNotFoundException {
        userDAO.getUser(node.get("userId").intValue());
        roomDAO.getRoom(node.get("roomId").intValue());
        return roomDAO.removeUser(node);
    }
}
