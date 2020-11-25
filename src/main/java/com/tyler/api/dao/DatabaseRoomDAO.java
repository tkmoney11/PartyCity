package com.tyler.api.dao;

import com.tyler.api.models.Room;
import com.tyler.api.models.User;

import javax.swing.plaf.synth.SynthOptionPaneUI;
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
    public Room getRoom(int id) {
        String sqlQuery = "SELECT * from rooms "
            + "WHERE id = " + id;
        Room room = null;
        try (Connection connection = getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                int hostId = rs.getInt(2);
                String gameType = rs.getString(3);
                room = new Room(id, hostId, gameType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return room;
    }

    /**
     *
     * @return all rooms
     */
    public Map<String, Room> getAllRooms() {
        String sqlQuery = "SELECT * FROM rooms";
        Map<String, Room> rooms = new HashMap<>();
        try (Connection connection = getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                int id = rs.getInt(1);
                int hostId = rs.getInt(2);
                String gameType = rs.getString(3);
                rooms.put((id + ""), new Room(id, hostId, gameType));
            }

            return rooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    /**
     *
     * @param hostId
     * @param gameType
     * Adds room to the DB
     */
    public void addRoom(int hostId, String gameType) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            String sqlQuery = "INSERT INTO rooms "
                    + "(hostId, gameType) "
                    + " VALUES"
                    + "(?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            // TODO pstmt set values here
            pstmt.setInt(1, hostId);
            pstmt.setString(2, gameType);
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     *
     * @param roomId
     * @param newHost
     * Allows you to edit the host of the
     */
    public void editHost(int roomId, int newHost) {
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

    public Map<String, Room> getRoomByName(String name) {

        String sqlQuery = "SELECT * FROM rooms "
                + "RIGHT OUTER JOIN users ON (rooms.hostId = users.id) "
                + "WHERE firstName = ?";
        Map<String, Room> rooms = new HashMap<>();

        try (Connection connection = getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int hostId = rs.getInt(2);
                String gameType = rs.getString(3);
                rooms.put((id + ""), new Room(id, hostId, gameType));
            }
            return rooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
