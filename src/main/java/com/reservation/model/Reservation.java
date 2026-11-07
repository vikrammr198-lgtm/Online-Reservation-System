package com.reservation.model;

import java.time.LocalDate;

public class Reservation {

    private String pnr;
    private int userId;
    private int trainNumber;
    private String passengerName;
    private String classType;
    private LocalDate journeyDate;
    private String sourceStation;
    private String destinationStation;

    public Reservation(String pnr, int userId, int trainNumber,
                       String passengerName, String classType,
                       LocalDate journeyDate, String sourceStation,
                       String destinationStation) {

        this.pnr = pnr;
        this.userId = userId;
        this.trainNumber = trainNumber;
        this.passengerName = passengerName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
    }

    public String getPnr() {
        return pnr;
    }

    public int getUserId() {
        return userId;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getClassType() {
        return classType;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }
}