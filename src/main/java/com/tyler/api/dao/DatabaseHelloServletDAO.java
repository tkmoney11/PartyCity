package com.tyler.api.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import org.postgresql.Driver;

import static com.tyler.api.util.JDBCUtility.getConnection;

// TODO deprecate. Does not connect to anything
public class DatabaseHelloServletDAO {

    public void testConnection() {
        try (Connection connection = getConnection()) {
            System.out.println("Connection works");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getAllUsers() {
        String sqlQuery = "SELECT * FROM persons";
        try (Connection connection = getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String username) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            String sqlQuery = "INSERT INTO Persons "
                            + "(PersonID, LastName, FirstName, Address, City) "
                            + " VALUES"
                            + "(?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

//            Date timeStamp
            // TODO pstmt set values here   

            if (pstmt.executeUpdate() != 1) {
                throw new SQLException("Inserting user failed, no rows were affected");
            }

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
