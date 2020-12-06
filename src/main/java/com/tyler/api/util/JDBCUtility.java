package com.tyler.api.util;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtility {
    /*
     * The purpose of JDBC is to have your application be able to communicate with a relational database
     * JDBC is an API included with Java (java.sql)
     *
     * JDBC is able to communicate with a variety of different databases
     * through the use of JDBC drivers
     *
     * These drivers are managed using the JDBC Driver Manager
     *
     * In order to obtain a connection, we need to have the driver loaded, and provide credentials such as our username, password, and
     * connection string
     *
     * Some important classes
     * 1. java.sql.DriverManager
     * 2. java.sql.Connection
     * 3. java.sql.Statement
     * 4. java.sql.ResultSet
     *
     * A general workflow might be
     * 1. Establish a connection
     * 2. Create a Statement object
     * 3. Execute a SQL query from that statement object
     * 4. Process data from the ResultSet
     *
     * WIthin this utility class, we will have a static method that retrieves a connection to the DB, or creates a connection
     */

    public static Connection getConnection() throws SQLException {
        /*
         * To obtain a connection, we need to provide credentials along with a connection string
         * The connection string is formatted like jdbc:<driver protocol>:<connection details>
         * jdbc:postgresql://localhost:5432/postgres
         */
        // Local host is doing shet
//        String url = "jdbc:postgresql://localhost:5433/postgres";
         String url = System.getenv("DB_URL");

        /*
         * Not really good practice to store directly in the code
         *
         * Ideally we should store them in url, username, password, in environment variables
         */



        Connection connection = null;

        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(url);
        return connection;
    }
}
