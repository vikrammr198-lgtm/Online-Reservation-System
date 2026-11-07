package com.reservation.gui;

import javax.swing.*;
import java.awt.*;
import com.reservation.service.ReservationService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import com.reservation.model.Train;
import java.util.List;

public class BookTicketFrame extends JFrame {

    private JTextField passengerNameField;
    private JTextField journeyDateField;
    private JTextField sourceField;
    private JTextField destinationField;

    private JComboBox<String> trainComboBox;
    private JComboBox<String> classComboBox;

    private JButton bookButton;
    private JButton backButton;

    private int userId;

    private boolean bookingInProgress = false;

    private ReservationService reservationService =
            new ReservationService();

    public BookTicketFrame(int userId) {

        this.userId = userId;

        setTitle("Book Train Ticket");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 15));

        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        passengerNameField = new JTextField();

        trainComboBox = new JComboBox<>();

        loadTrains();

        String[] classes = {
                "Sleeper",
                "AC",
                "General"
        };

        classComboBox = new JComboBox<>(classes);

        journeyDateField = new JTextField();

        sourceField = new JTextField();
        destinationField = new JTextField();

        bookButton = new JButton("Book Ticket");
        backButton = new JButton("Back");

        panel.add(new JLabel("Passenger Name:"));
        panel.add(passengerNameField);

        panel.add(new JLabel("Train:"));
        panel.add(trainComboBox);

        panel.add(new JLabel("Class Type:"));
        panel.add(classComboBox);

        panel.add(new JLabel("Journey Date (YYYY-MM-DD):"));
        panel.add(journeyDateField);

        panel.add(new JLabel("Source Station:"));
        panel.add(sourceField);

        panel.add(new JLabel("Destination Station:"));
        panel.add(destinationField);

        panel.add(backButton);
        panel.add(bookButton);

        add(panel);

        backButton.addActionListener(e -> dispose());

        bookButton.addActionListener(e -> bookTicket());
    }

    private void loadTrains() {

        List<Train> trains = reservationService.getAllTrains();

        if (trains.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load trains. Please check database connection or train availability!",
                    "Train Loading Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        for (Train train : trains) {

            String trainDetails =
                    train.getTrainNumber() + " - " + train.getTrainName();

            trainComboBox.addItem(trainDetails);
        }
    }

    private void bookTicket() {

        if (bookingInProgress) {
            return;
        }

        bookingInProgress = true;
        bookButton.setEnabled(false);

        try {

            // Validate passenger name
            String passengerName =
                    passengerNameField.getText().trim();

            if (passengerName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Passenger name cannot be empty!",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (!passengerName.matches("[a-zA-Z ]+")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Passenger name should contain only letters and spaces!",
                        "Invalid Passenger Name",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (passengerName.length() > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Passenger name cannot exceed 100 characters!",
                        "Invalid Passenger Name",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Validate train availability
            if (trainComboBox.getItemCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No trains are currently available!",
                        "Train Availability",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Get selected train
            String selectedTrain =
                    (String) trainComboBox.getSelectedItem();

            if (selectedTrain == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a train!",
                        "Train Selection",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int trainNumber = Integer.parseInt(
                    selectedTrain.split(" - ")[0]
            );

            // Get class type
            String classType =
                    (String) classComboBox.getSelectedItem();

            // Validate journey date
            String dateInput =
                    journeyDateField.getText().trim();

            if (dateInput.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Journey date cannot be empty!",
                        "Date Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            LocalDate journeyDate = LocalDate.parse(dateInput);

            if (journeyDate.isBefore(LocalDate.now())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Journey date cannot be in the past!",
                        "Invalid Journey Date",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Validate source and destination
            String source = sourceField.getText().trim();

            String destination =
                    destinationField.getText().trim();

            if (source.isEmpty() || destination.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Source and destination cannot be empty!",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (!source.matches("[a-zA-Z ]+")
                    || !destination.matches("[a-zA-Z ]+")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Station names should contain only letters and spaces!",
                        "Invalid Station Name",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (source.length() > 100 || destination.length() > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Station names cannot exceed 100 characters!",
                        "Invalid Station Name",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (source.equalsIgnoreCase(destination)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Source and destination cannot be the same!",
                        "Invalid Route",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Call ReservationService
            String result = reservationService.bookTicket(
                    userId,
                    trainNumber,
                    passengerName,
                    classType,
                    journeyDate,
                    source,
                    destination
            );

            // Check booking result
            if (result != null && result.matches("[A-F0-9]{12}")) {

                JOptionPane.showMessageDialog(
                        this,
                        "TICKET BOOKED SUCCESSFULLY!\n\n"
                                + "PNR Number: " + result + "\n"
                                + "Passenger Name: " + passengerName + "\n"
                                + "Train: " + selectedTrain + "\n"
                                + "Class: " + classType + "\n"
                                + "Journey Date: " + journeyDate + "\n"
                                + "Source: " + source + "\n"
                                + "Destination: " + destination,
                        "Booking Confirmation",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        result,
                        "Booking Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid date in YYYY-MM-DD format!",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid train number!",
                    "Train Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            bookingInProgress = false;
            bookButton.setEnabled(true);
        }
    }
}