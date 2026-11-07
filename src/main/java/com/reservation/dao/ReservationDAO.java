package com.reservation.dao;

import com.reservation.database.DBConnection;
import com.reservation.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public boolean bookTicket(Reservation reservation) {

        String sql = """
                INSERT INTO reservations
                (pnr, user_id, train_number, passenger_name,
                 class_type, journey_date, source_station,
                 destination_station)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservation.getPnr());
            ps.setInt(2, reservation.getUserId());
            ps.setInt(3, reservation.getTrainNumber());
            ps.setString(4, reservation.getPassengerName());
            ps.setString(5, reservation.getClassType());
            ps.setDate(6, java.sql.Date.valueOf(reservation.getJourneyDate()));
            ps.setString(7, reservation.getSourceStation());
            ps.setString(8, reservation.getDestinationStation());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

public Reservation getReservation(String pnr) {

    String sql = "SELECT * FROM reservations WHERE pnr = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, pnr);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            return new Reservation(
                rs.getString("pnr"),
                rs.getInt("user_id"),
                rs.getInt("train_number"),
                rs.getString("passenger_name"),
                rs.getString("class_type"),
                rs.getDate("journey_date").toLocalDate(),
                rs.getString("source_station"),
                rs.getString("destination_station")
            );
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return null;
}

public boolean cancelReservation(String pnr, int userId) {

    String sql = "DELETE FROM reservations WHERE pnr = ? AND user_id = ?";

    try (Connection con = DBConnection.getConnection()) {

        if (con == null) {
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
public List<Reservation> getReservationsByUserId(int userId) {

    List<Reservation> reservations = new ArrayList<>();

    String sql = "SELECT * FROM reservations WHERE user_id = ?";

    try (Connection con = DBConnection.getConnection()) {

        if (con == null) {
            return reservations;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Reservation reservation = new Reservation(
                            rs.getString("pnr"),
                            rs.getInt("user_id"),
                            rs.getInt("train_number"),
                            rs.getString("passenger_name"),
                            rs.getString("class_type"),
                            rs.getDate("journey_date").toLocalDate(),
                            rs.getString("source_station"),
                            rs.getString("destination_station")
                    );

                    reservations.add(reservation);
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return reservations;
}
}