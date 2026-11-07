package com.reservation.dao;

import com.reservation.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean validateLogin(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, username);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {

                    return rs.next();
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
    public int getUserId(String username) {

    String sql = "SELECT user_id FROM users WHERE username = ?";

    try (Connection con = DBConnection.getConnection()) {

        if (con == null) {
            return -1;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return -1;
}
public boolean registerUser(String username, String password) {

    String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

    try (Connection con = DBConnection.getConnection()) {

        if (con == null) {
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        }

    } catch (SQLException e) {

        e.printStackTrace();
        return false;
    }
}
public boolean isUsernameExists(String username) {

    String sql = "SELECT user_id FROM users WHERE username = ?";

    try (Connection con = DBConnection.getConnection()) {

        if (con == null) {
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                return rs.next();
            }
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return false;
}
}