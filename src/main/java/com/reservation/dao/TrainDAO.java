package com.reservation.dao;

import com.reservation.database.DBConnection;
import com.reservation.model.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {

    public Train getTrainByNumber(int trainNumber) {

        String sql = "SELECT * FROM trains WHERE train_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, trainNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Train(
                    rs.getInt("train_number"),
                    rs.getString("train_name")
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }
    public List<Train> getAllTrains() {

    List<Train> trains = new ArrayList<>();

    String sql = "SELECT * FROM trains";

    try (Connection con = DBConnection.getConnection()) {

        if (con == null) {
            return trains;
        }

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Train train = new Train(
                        rs.getInt("train_number"),
                        rs.getString("train_name")
                );

                trains.add(train);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return trains;
}
}