package db;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestInsertUser {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (name,email,password,user_type,mobile) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "Test User");
            stmt.setString(2, "testuser@example.com");
            stmt.setString(3, "123456");
            stmt.setString(4, "donor");
            stmt.setString(5, "9999999999");
            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
