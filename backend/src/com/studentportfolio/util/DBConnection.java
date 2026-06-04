package com.studentportfolio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_portfolio_db";

    private static final String USER = "root";

    private static final String PASSWORD = "Root@123";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Database Connected Successfully");
        return connection;
    }
}