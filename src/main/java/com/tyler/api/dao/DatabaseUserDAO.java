package com.tyler.api.dao;

import com.tyler.api.exceptions.UserNotFoundException;
import com.tyler.api.models.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.tyler.api.util.JDBCUtility.getConnection;

public class DatabaseUserDAO {

    public User editUserPassword(int id, String newPassword) throws UserNotFoundException {
        String sqlQuery = "UPDATE users "
                + "SET password = ? "
                + "WHERE id = ?";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, newPassword);
            pstmt.setInt(2, id);

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

        return getUser(id);
    }

    public void deleteUser(int id) {
        String sqlQuery = "DELETE FROM users WHERE id = (?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1, id);
            pstmt.execute();
            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public Map<Integer, User> getAllUsers() {
        String sqlQuery = "SELECT * FROM users";
        Map<Integer, User> users = new HashMap<>();
        try (Connection connection = getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String email = rs.getString(4);
                String username = rs.getString(5);
                String password = rs.getString(6);
                boolean administrator = rs.getBoolean(7);
                users.put(id, new User(id, firstName, lastName, email, username, password, administrator));
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUser(int uid) throws UserNotFoundException {
        String sqlQuery = "SELECT * from Users "
                + "WHERE id = " + uid;
        User user = null;
        try (Connection connection = getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
//                System.out.println("before");
//                java.util.Date createdAt = new java.util.Date(rs.getDate(3).getTime());
//                System.out.println("after");
                String email = rs.getString(4);
                String username = rs.getString(5);
                String password = rs.getString(6);
                boolean administrator = rs.getBoolean(7);
                user = new User(uid, firstName, lastName, email, username, password, administrator);
            }
            if (user == null)
                throw new UserNotFoundException();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    public User getUser(String username) throws UserNotFoundException, SQLException {
        String sqlQuery = "SELECT * from users "
                + "WHERE username = ?";
        User user = null;
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id  = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
//                System.out.println("before");
//                java.util.Date createdAt = new java.util.Date(rs.getDate(3).getTime());
//                System.out.println("after");
                String email = rs.getString(4);
                String userName = rs.getString(5);
                String password = rs.getString(6);
                boolean administrator = rs.getBoolean(7);
                user = new User(id, firstName, lastName, email, userName, password, administrator);
            }
            if (user == null)
                throw new UserNotFoundException();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public User addUser(String firstName, String lastName,
                        String email, String username, String password) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            String sqlQuery = "INSERT INTO users "
                    + "(firstName, lastName, email, username, password) "
                    + " VALUES"
                    + "(?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            // TODO pstmt set values here
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
//            pstmt.setDate(3, new Date(new java.util.Date().getTime()));
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password);

            if (pstmt.executeUpdate() != 1) {
                throw new SQLException("Inserting destination failed, no rows were affected");
            }

            // generateID
            int autoId = 0;
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                autoId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Inserting user failed, no ID generated");
            }

            // Return User with autoId
            connection.commit();
            return getUser(autoId);

        } catch (SQLException | UserNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public boolean authenticateVersionOne(String username, String password) {

        try (Connection connection = getConnection()) {
            String sqlQuery = "SELECT * FROM users " +
                    "WHERE username = ? AND password = ?";

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            // set preparedstatment values here
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return true;

            return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public User flipAdmin(int id) throws UserNotFoundException {
        String sqlQuery = "UPDATE users "
                + "set administrator = not administrator "
                + "WHERE id = ?";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, id);

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
        return getUser(id);
    }
}
