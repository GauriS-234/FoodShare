package db;

import db.DBConnection;
import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Connected to database: " + conn.getCatalog());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

