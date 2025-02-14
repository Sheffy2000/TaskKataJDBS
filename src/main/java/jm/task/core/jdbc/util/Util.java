package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/fortask2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ClassiC997521";

    private static Connection connection = null;

    private Util() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection ( URL, USERNAME, PASSWORD );
            } catch (SQLException e) {
                e.printStackTrace ();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close ();
            } catch (SQLException e) {
                e.printStackTrace ();
            }
        }
    }
}
