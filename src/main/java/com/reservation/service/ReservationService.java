package com.reservation.service;

import com.reservation.dao.ReservationDAO;
import com.reservation.dao.TrainDAO;
import com.reservation.model.Reservation;
import com.reservation.model.Train;

import java.time.LocalDate;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import com.reservation.model.Reservation;

public class ReservationService {

    private ReservationDAO reservationDAO = new ReservationDAO();

    private TrainDAO trainDAO = new TrainDAO();


    // 1. Book Ticket
    public String bookTicket(
            int userId,
            int trainNumber,
            String passengerName,
            String classType,
            LocalDate journeyDate,
            String source,
            String destination) {

        // Input validation
        if (passengerName == null || passengerName.isBlank()
                || classType == null || classType.isBlank()
                || source == null || source.isBlank()
                || destination == null || destination.isBlank()) {

            return "All fields are required!";
        }

        if (journeyDate == null || journeyDate.isBefore(LocalDate.now())) {
            return "Invalid journey date!";
        }

        if (source.equalsIgnoreCase(destination)) {
            return "Source and destination cannot be the same!";
        }

        // Check whether train exists
        Train train = trainDAO.getTrainByNumber(trainNumber);

        if (train == null) {
            return "Train not found!";
        }

        // Generate unique PNR
        String pnr = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();

        // Create Reservation object
        Reservation reservation = new Reservation(
                pnr,
                userId,
                trainNumber,
                passengerName,
                classType,
                journeyDate,
                source,
                destination
        );

        // Save reservation
        boolean success = reservationDAO.bookTicket(reservation);

        if (success) {
            return pnr;
        }

        return "Booking failed!";
    }


    // 2. Fetch Reservation
    public Reservation getReservation(String pnr) {

        if (pnr == null || pnr.isBlank()) {
            return null;
        }

        return reservationDAO.getReservation(pnr);
    }


    // 3. Cancel Reservation
    public boolean cancelReservation(String pnr, int userId) {

    if (pnr == null || pnr.trim().isEmpty()) {
        return false;
    }

    return reservationDAO.cancelReservation(pnr, userId);
}
public List<Reservation> getReservationsByUserId(int userId) {

    if (userId <= 0) {
        return new ArrayList<>();
    }

    return reservationDAO.getReservationsByUserId(userId);
}
public List<Train> getAllTrains() {

    return trainDAO.getAllTrains();
}
}