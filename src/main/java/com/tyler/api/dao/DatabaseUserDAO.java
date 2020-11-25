package com.tyler.api.dao;

import com.tyler.api.models.User;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.tyler.api.util.JDBCUtility.getConnection;

public class DatabaseUserDAO {

    public void editUserPassword(int id, String newPassword) {
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
    public Map<String, User> getAllUsers() {
        String sqlQuery = "SELECT * FROM users";
        Map<String, User> users = new HashMap<>();
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
                users.put((id + ""), new User(id, firstName, lastName, email, username, password));
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUser(int uid) {
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
                user = new User(uid, firstName, lastName, email, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    public void addUser(String firstName, String lastName,
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
}
