package com.reservation.gui;

import com.reservation.model.Reservation;
import com.reservation.service.ReservationService;

import javax.swing.*;
import java.awt.*;

public class ViewReservationFrame extends JFrame {

    private JTextField pnrField;
    private JButton searchButton;
    private JButton backButton;
    private JTextArea resultArea;

    private ReservationService reservationService =
            new ReservationService();

    public ViewReservationFrame() {

        setTitle("View Reservation");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        panel.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        // Top panel
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        pnrField = new JTextField();

        searchButton = new JButton("Search");

        topPanel.add(new JLabel("Enter PNR Number:"));
        topPanel.add(pnrField);
        topPanel.add(new JLabel(""));
        topPanel.add(searchButton);

        // Result area
        resultArea = new JTextArea();

        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Back button
        backButton = new JButton("Back");

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);

        // Search button action
        searchButton.addActionListener(e -> searchReservation());

        // Back button action
        backButton.addActionListener(e -> dispose());
    }

    private void searchReservation() {

        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please enter PNR number!"
            );

            return;
        }

        Reservation reservation =
                reservationService.getReservation(pnr);

        if (reservation == null) {

            resultArea.setText("No reservation found!");

        } else {

            resultArea.setText(
                "========== RESERVATION DETAILS ==========\n\n"
                + "PNR Number: " + reservation.getPnr() + "\n"
                + "Passenger Name: " + reservation.getPassengerName() + "\n"
                + "Train Number: " + reservation.getTrainNumber() + "\n"
                + "Class Type: " + reservation.getClassType() + "\n"
                + "Journey Date: " + reservation.getJourneyDate() + "\n"
                + "Source: " + reservation.getSourceStation() + "\n"
                + "Destination: " + reservation.getDestinationStation()
            );
        }
    }
}