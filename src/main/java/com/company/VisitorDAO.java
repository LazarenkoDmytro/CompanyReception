package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitorDAO {
    private Connection conn;

    public VisitorDAO() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection to the MySQL server
            String url = "jdbc:mysql://localhost:3306/";
            String username = "root";
            String password = "4MXTqS[0?@63";
            conn = DriverManager.getConnection(url, username, password);
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Insert visitor into database
    public void insertVisitor(Visitor visitor) {
        String sql = "INSERT INTO Visitors (FirstName, LastName, Company_from, Visitor_ID, Photo, Staff_visiting, Office_visiting, date_begin, date_end) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, visitor.getFirstName());
            pstmt.setString(2, visitor.getLastName());
            pstmt.setString(3, visitor.getCompanyFrom());
            pstmt.setString(4, visitor.getVisitorId());
            pstmt.setString(5, visitor.getPhotoPath());
            pstmt.setString(6, visitor.getStaffVisitingName());
            pstmt.setString(7, visitor.getOfficeNo());
            pstmt.setTimestamp(8, visitor.getDateTimeIn());
            pstmt.setTimestamp(9, visitor.getDateTimeOut());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update visitor in the database
    public void updateVisitor(Visitor visitor) {
        String sql = "UPDATE Visitors SET FirstName = ?, LastName = ?, Company_from = ?, Photo = ?, Staff_visiting = ?, " +
                "Office_visiting = ?, date_begin = ?, date_end = ? WHERE Visitor_ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, visitor.getFirstName());
            pstmt.setString(2, visitor.getLastName());
            pstmt.setString(3, visitor.getCompanyFrom());
            pstmt.setString(4, visitor.getPhotoPath());
            pstmt.setString(5, visitor.getStaffVisitingName());
            pstmt.setString(6, visitor.getOfficeNo());
            pstmt.setTimestamp(7, visitor.getDateTimeIn());
            pstmt.setTimestamp(8, visitor.getDateTimeOut());
            pstmt.setString(9, visitor.getVisitorId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve visitor from the database
    public Visitor getVisitor(String visitorId) {
        String sql = "SELECT * FROM Visitors WHERE Visitor_ID = ?";
        Visitor visitor = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, visitorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                visitor = new Visitor(rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Company_from"), rs.getString("Visitor_ID"), rs.getString("Photo"),
                        rs.getString("Staff_visiting"), rs.getString("Office_visiting"), rs.getTimestamp("date_begin"), rs.getTimestamp("date_end"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visitor;
    }

    public List<Visitor> getVisitorsByDate(java.sql.Date date) {
        String sql = "SELECT * FROM Visitors WHERE DATE(date_begin) = ?";
        List<Visitor> visitors = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, date);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Visitor visitor = new Visitor(rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Company_from"), rs.getString("Visitor_ID"), rs.getString("Photo"),
                        rs.getString("Staff_visiting"), rs.getString("Office_visiting"),
                        rs.getTimestamp("date_begin"), rs.getTimestamp("date_end"));
                visitors.add(visitor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visitors;
    }

    public void initialize() {
        String dropDatabaseSQL = "DROP DATABASE IF EXISTS company;";
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS company;";
        String useDatabaseSQL = "USE company;";
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS Visitors (
                   id int primary key auto_increment,
                   FirstName varchar(30) NOT NULL,
                   LastName varchar(30) NOT NULL,
                   Company_from varchar(15),
                   Visitor_ID varchar(10) NOT NULL,
                   Photo varchar(100),
                   Staff_visiting varchar(60),
                   Office_visiting varchar(5),
                   date_begin timestamp NOT NULL,
                   date_end timestamp NULL
                );""";
        String insertDataSQL = "INSERT INTO Visitors (FirstName,LastName,Company_from,Visitor_ID,Photo,Staff_visiting,Office_visiting,date_begin,date_end)\n" +
                "VALUES ('Carlitos', 'Colon', 'IBM', '180578', 'js342a.jpg', 'Anibal Ramirez', 'L305', '2018-09-26 09:12:23', '2018-09-26 10:55:36');";

        try {
            PreparedStatement pstmt = conn.prepareStatement(dropDatabaseSQL);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(createDatabaseSQL);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(useDatabaseSQL);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(createTableSQL);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(insertDataSQL);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
