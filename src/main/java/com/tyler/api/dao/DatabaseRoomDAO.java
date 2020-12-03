package com.tyler.api.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tyler.api.exceptions.RoomNotFoundException;
import com.tyler.api.exceptions.UserNotFoundException;
import com.tyler.api.models.Room;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.tyler.api.util.JDBCUtility.getConnection;

public class DatabaseRoomDAO {

    public DatabaseRoomDAO() {
        super();
    }

    /**
     *
     * @param id
     * @return one room by roomId
     */
    public Room getRoom(int id) throws RoomNotFoundException {
        String sqlQuery = "SELECT R.id, R.hostId, R.game, RU.user_id " +
                "FROM rooms as R " +
                "INNER JOIN rooms_users as RU on RU.room_Id=R.id " +
                "WHERE R.id = " + id;
        Room room = null;
        try (Connection connection = getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                if (room == null) {
                    int hostId = rs.getInt(2);
                    String gameType = rs.getString(3);
                    room = new Room(id, hostId, gameType);
                } else {
                    room.addUser(rs.getInt(4));
                }
            }
            if (room == null)
                throw new RoomNotFoundException();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return room;
    }

    /**
     *
     * @return all rooms
     */
    public Map<Integer, Room> getAllRooms() {
        String sqlQuery = "SELECT R.id, R.hostId, R.game, RU.user_id " +
                "FROM rooms as R " +
                "INNER JOIN rooms_users as RU on RU.room_Id=R.id";

        Map<Integer, Room> rooms = new HashMap<>();
        try (Connection connection = getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                int id = rs.getInt(1);
                if (!rooms.containsKey(id)) {
                    int hostId = rs.getInt(2);
                    String gameType = rs.getString(3);
                    rooms.put(id, new Room(id, hostId, gameType));
                } else {
                    rooms.get(id).addUser(rs.getInt(4));
                }
            }

            // PULL USERS FROM DB
            return rooms;
        } catch (SQLException e) {
            rooms.put(-1, new Room());
            e.printStackTrace();
            return rooms;
        }
    }

    /**
     *
     * @param room
     * Adds room to the DB
     */
    public Room addRoom(Room room) throws UserNotFoundException {
        try {
            DatabaseUserDAO userDAO = new DatabaseUserDAO();
            userDAO.getUser(room.getHostId());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        }
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            String sqlQuery = "INSERT INTO rooms "
                    + "(hostId, game) "
                    + " VALUES "
                    + "(?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            // TODO pstmt set values here
            pstmt.setInt(1, room.getHostId());
            pstmt.setString(2, room.getGame());
//            pstmt.setDate(3, new Date(new java.util.Date().getTime()));
            if (pstmt.executeUpdate() != 1) {
                throw new SQLException("Inserting user failed, no rows were affected");
            }

            // generateID
            int autoId = 0;
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                autoId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Inserting user failed, no ID generated");
            }

            connection.commit();
            return new Room(autoId, room.getHostId(), room.getGame());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Room();
        }
    }

    /**
     *
     * @param room
     * @return Final Room object added to DB
     * This is an auxiliary method.
     * TODO REVIEW AND ADD INTO DAO METHOD IF POSSIBLE WHEN YOU HAVE TIME
     */
    public Room addToRoomsUsers(Room room) {
        String sqlQuery = "INSERT INTO rooms_users "
                + "(room_id, user_id) "
                + "VALUES "
                + "(?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            // TODO pstmt set values
            pstmt.setInt(1, room.getId());
            pstmt.setInt(2, room.getHostId());

            if (pstmt.executeUpdate() != 1) {
                throw new SQLException("Inserting user failed, no rows were affected");
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }


    public Room addUser(ObjectNode node) {
        int roomId = node.get("roomId").intValue();
        int userId = node.get("userId").intValue();
        String sqlQuery = "INSERT INTO rooms_users (room_id, user_id)"
                + "VALUES (?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1, roomId);
            pstmt.setInt(2, userId);

            if (pstmt.executeUpdate() != 1) {
                throw new SQLException("Inserting destination failed, no rows were affected");
            }

            connection.commit();
            return getRoom(roomId);
        } catch (SQLException | RoomNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return new Room();
    }

    public Room removeUser(ObjectNode node) {
        int roomId = node.get("roomId").intValue();
        int userId = node.get("userId").intValue();
        String sqlQuery = "DELETE FROM rooms_users WHERE room_id = ? AND user_id = ?";
        Room room = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1, roomId);
            pstmt.setInt(2, userId);
            pstmt.execute();
            connection.commit();
            return new Room(roomId, userId, "User " + userId + " deleted from Room " + roomId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return room;
    }


    public Map<Integer, Room> getRoomByName(String name) throws UserNotFoundException {

        String sqlQuery = "SELECT  R.id, R.hostid, R.game, U.id "
                + "FROM rooms R "
                + "FULL JOIN rooms_users RU ON R.id=RU.room_id "
                + "INNER JOIN users U ON U.id=RU.user_id "
                + "WHERE U.username = ?";
        Map<Integer, Room> rooms = new HashMap<>();

        try (Connection connection = getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                if (!rooms.containsKey(id)) {
                    int hostId = rs.getInt(2);
                    String gameType = rs.getString(3);
                    rooms.put(id, new Room(id, hostId, gameType));
                } else {
                    rooms.get(id).addUser(rs.getInt(4));
                }
            }
            System.out.println(rooms.size());
            if (rooms.size() == 0)
                throw new UserNotFoundException();
            return rooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    //TODO finish editing/Deleting Rooms
    // TODO finish/polish usersServlet
    //  TODO finish documentation
    //   TODO maybe do unit tests
    //   TODO maybe finish logging
    //    TODO Host on EC2

    /**
     *  @param roomId
     * @param newHost
     * @return
     */
    public Room editHost(int roomId, int newHost) throws RoomNotFoundException {
        String sqlQuery = "UPDATE rooms "
                + "SET hostId = ? "
                + "WHERE id = ?";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, newHost);
            pstmt.setInt(2, roomId);

            if (pstmt.executeUpdate() != 1) {
                throw new SQLException("Inserting destination failed, no rows were affected");
            }

            int autoId = 0;
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                autoId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Inserting destination failed, no ID generated.");
            }
            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getRoom(roomId);
    }

    public void deleteRoom(int roomId) {
        String sqlQuery = "DELETE FROM rooms WHERE id = (?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1, roomId);
            pstmt.execute();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
