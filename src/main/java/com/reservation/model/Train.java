package com.reservation.model;

public class Train {

    private int trainNumber;
    private String trainName;

    public Train(int trainNumber, String trainName) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }
}